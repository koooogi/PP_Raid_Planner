const API_URL = 'http://localhost:8080';

let currentUser = null;
let selectedCrewIds = [];
let routeIds = [];
let shipsData = [];
let settlementsData = [];

function calculateDistance(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

async function apiRequest(endpoint, method = 'GET', body = null) {
    const options = {
        method: method,
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        }
    };
    
    if (body) {
        options.body = JSON.stringify(body);
    }
    
    const response = await fetch(`${API_URL}${endpoint}`, options);
    
    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || `HTTP ${response.status}`);
    }
    
    return response.json();
}

async function loadShips() {
    shipsData = await apiRequest('/api/ships');
    const select = document.getElementById('shipSelect');
    select.innerHTML = '<option value="">-- Select a ship --</option>';
    shipsData.forEach(ship => {
        const option = document.createElement('option');
        option.value = ship.id;
        option.textContent = `${ship.name} (Max rowers: ${ship.maxRowers}, Cargo: ${ship.maxCargo})`;
        option.title = ship.description;
        select.appendChild(option);
    });
    
    select.addEventListener('change', (e) => {
        const shipDesc = document.getElementById('shipDescription');
        const shipDescText = document.getElementById('shipDescText');
        if (e.target.value) {
            const selectedShip = shipsData.find(s => s.id == e.target.value);
            if (selectedShip && selectedShip.description) {
                shipDescText.textContent = selectedShip.description;
                shipDesc.style.display = 'block';
            } else {
                shipDesc.style.display = 'none';
            }
        } else {
            shipDesc.style.display = 'none';
        }
    });
}

async function loadCrew() {
    const crew = await apiRequest('/api/crew');
    const container = document.getElementById('crewList');
    container.innerHTML = '';
    
    crew.forEach(member => {
        const div = document.createElement('div');
        div.className = 'crew-item';
        div.innerHTML = `
            <input type="checkbox" value="${member.id}" id="crew_${member.id}">
            <label for="crew_${member.id}">
                <strong>${member.name}</strong><br>
                <small>Clan: ${member.clan} | Gender: ${member.gender} | Age: ${member.age}</small><br>
                <small>Strength: ${member.strength}, Supplies: ${member.supplies}</small>
            </label>
        `;
        const checkbox = div.querySelector('input');
        checkbox.addEventListener('change', (e) => {
            if (e.target.checked) {
                if (!selectedCrewIds.includes(member.id)) {
                    selectedCrewIds.push(member.id);
                }
            } else {
                selectedCrewIds = selectedCrewIds.filter(id => id !== member.id);
            }
            updateCrewStats();
        });
        container.appendChild(div);
    });
}

async function loadSettlements() {
    settlementsData = await apiRequest('/api/settlements');
    const container = document.getElementById('settlementsList');
    container.innerHTML = '';
    
    settlementsData.forEach(settlement => {
        const distanceFromStart = calculateDistance(0, 0, settlement.x, settlement.y);
        
        const div = document.createElement('div');
        div.className = 'settlement-item';
        div.innerHTML = `
            <div>
                <strong>${settlement.name}</strong>
                <small>(${settlement.type})</small>
                <div style="font-size: 12px; color: #8ba888;">Scale: ${settlement.scale}, Loot: ${settlement.baseLoot}</div>
                <div style="font-size: 11px; color: #c77dff;">📏 Distance from start: ${distanceFromStart.toFixed(1)} units</div>
                <div style="font-size: 11px; margin-top: 5px;">${settlement.description || ''}</div>
            </div>
        `;
        div.addEventListener('click', () => {
            if (!routeIds.includes(settlement.id)) {
                routeIds.push(settlement.id);
                renderRoute();
            }
        });
        container.appendChild(div);
    });
}

function renderRoute() {
    const container = document.getElementById('selectedRoute');
    container.innerHTML = '';
    
    let cumulativeDistance = 0;
    let prevX = 0;
    let prevY = 0;
    
    routeIds.forEach((id, index) => {
        const settlement = settlementsData.find(s => s.id == id);
        if (settlement) {
            const distanceFromPrev = calculateDistance(prevX, prevY, settlement.x, settlement.y);
            cumulativeDistance += distanceFromPrev;
            
            const li = document.createElement('li');
            li.innerHTML = `
                <div>
                    <strong>${index + 1}. ${settlement.name}</strong>
                    <div style="font-size: 11px; color: #8ba888;">
                        Distance from previous: ${distanceFromPrev.toFixed(1)} units
                        ${index === 0 ? ' (from start)' : ''}
                    </div>
                    <div style="font-size: 11px; color: #c77dff;">
                        Total distance so far: ${cumulativeDistance.toFixed(1)} units
                    </div>
                </div>
                <button class="remove-route" data-index="${index}">✖</button>
            `;
            container.appendChild(li);
            
            prevX = settlement.x;
            prevY = settlement.y;
        } else {
            const li = document.createElement('li');
            li.innerHTML = `
                ${index + 1}. Settlement ID: ${id}
                <button class="remove-route" data-index="${index}">✖</button>
            `;
            container.appendChild(li);
        }
    });
    
    document.querySelectorAll('.remove-route').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const index = parseInt(e.target.dataset.index);
            routeIds.splice(index, 1);
            renderRoute();
        });
    });
}

function updateCrewStats() {
    const stats = document.getElementById('crewStats');
    stats.textContent = `Selected: ${selectedCrewIds.length} warriors`;
}

async function startSimulation() {
    const shipId = document.getElementById('shipSelect').value;
    if (!shipId) {
        alert('Select a ship!');
        return;
    }
    if (selectedCrewIds.length === 0) {
        alert('Select at least one warrior!');
        return;
    }
    if (routeIds.length === 0) {
        alert('Select at least one settlement!');
        return;
    }
    
    const requestBody = {
        shipId: parseInt(shipId),
        crewIds: selectedCrewIds,
        settlementIds: routeIds
    };
    
    try {
        const expedition = await apiRequest('/api/expeditions', 'POST', requestBody);
        const result = await apiRequest(`/api/expeditions/${expedition.id}/simulate`, 'POST');
        
        const resultCard = document.getElementById('resultCard');
        const resultContent = document.getElementById('resultContent');
        resultCard.classList.remove('hidden');
        
        if (result.feasible) {
            let lootText = '';
            if (result.lootByType && Object.keys(result.lootByType).length > 0) {
                lootText = Object.entries(result.lootByType).map(([k, v]) => `  • ${k}: ${v}`).join('\n');
            } else {
                lootText = '  • No loot';
            }
            
            resultContent.innerHTML = `
✅ RAID SUCCESSFUL!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📅 Total days: ${result.totalDays}
📏 Total distance: ${result.totalDistance?.toFixed(1) || 0} units
💰 Total loot value: ${result.totalLootValue}
👥 Slaves captured: ${result.totalSlaves}
📦 Supplies consumed: ${result.totalSuppliesConsumed}
🍞 Supplies available: ${result.totalSuppliesAvailable}

📊 Loot breakdown:
${lootText}
`;
        } else {
            resultContent.innerHTML = `
❌ RAID FAILED!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Reason: ${result.failureReason}
`;
        }
        
        loadHistory();
    } catch (error) {
        console.error('Simulation error:', error);
        alert('Error: ' + error.message);
    }
}

async function loadHistory() {
    try {
        const history = await apiRequest('/api/expeditions/history');
        const container = document.getElementById('historyContent');
        if (!history || history.length === 0) {
            container.innerHTML = 'No raids yet';
            return;
        }
        container.innerHTML = history.map(h => `
            <div class="history-item ${h.status === 'SUCCESS' ? 'success' : 'failed'}">
                <strong>${h.shipName || 'Ship'}</strong> - ${h.status}<br>
                <small>Crew: ${h.crewCount || 0}, Settlements: ${h.settlementCount || 0}</small><br>
                <small>Days: ${h.totalDays || 0}, Loot: ${h.totalLoot || 0}, Slaves: ${h.totalSlaves || 0}</small>
                ${h.failureReason ? `<br><small>❌ ${h.failureReason}</small>` : ''}
            </div>
        `).join('');
    } catch (e) {
        console.error('Failed to load history:', e);
    }
}

async function login(username, password) {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    
    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params
    });
    
    if (!response.ok) throw new Error('Login failed');
    return response.json();
}

async function register(username, password) {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    
    const response = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: params
    });
    
    if (!response.ok) throw new Error('Registration failed');
    return response.json();
}

document.getElementById('loginBtn').addEventListener('click', async () => {
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    try {
        await login(username, password);
        currentUser = username;
        document.getElementById('authSection').classList.add('hidden');
        document.getElementById('mainContent').classList.remove('hidden');
        loadShips();
        loadCrew();
        loadSettlements();
        loadHistory();
    } catch (e) {
        alert('Login failed: ' + e.message);
    }
});

document.getElementById('registerBtn').addEventListener('click', async () => {
    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;
    try {
        await register(username, password);
        alert('Registration successful! You can now login.');
    } catch (e) {
        alert('Registration failed: ' + e.message);
    }
});

document.getElementById('simulateBtn').addEventListener('click', startSimulation);