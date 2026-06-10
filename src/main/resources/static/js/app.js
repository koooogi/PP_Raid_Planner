const API_URL = 'http://localhost:8080';

let currentUser = null;
let selectedCrewIds = [];
let routeIds = [];
let shipsData = [];
let settlementsData = [];
let crewData = [];

function calculateDistance(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

async function apiRequest(endpoint, method = 'GET', body = null) {
    const options = {
        method: method,
        credentials: 'include',
        headers: {}
    };
    
    if (body) {
        options.headers['Content-Type'] = 'application/json';
        options.body = JSON.stringify(body);
    }
    
    const response = await fetch(`${API_URL}${endpoint}`, options);
    if (!response.ok) {
        throw new Error(await response.text() || `HTTP ${response.status}`);
    }
    return response.json();
}

// Проверка лимита гребцов
function checkCrewLimit() {
    const select = document.getElementById('shipSelect');
    const selectedOption = select.options[select.selectedIndex];
    const maxRowers = selectedOption ? parseInt(selectedOption.getAttribute('data-max-rowers')) : 0;
    const crewCount = selectedCrewIds.length;
    
    const crewStats = document.getElementById('crewStats');
    const simulateBtn = document.getElementById('simulateBtn');
    
    if (maxRowers > 0 && crewCount > maxRowers) {
        crewStats.innerHTML = `⚠️ Selected: ${crewCount} warriors (EXCEEDS LIMIT of ${maxRowers})!`;
        crewStats.style.color = '#ff6b6b';
        if (simulateBtn) simulateBtn.disabled = true;
        return false;
    } else {
        crewStats.innerHTML = `Selected: ${crewCount} warriors`;
        crewStats.style.color = '#c5d6e8';
        if (simulateBtn) simulateBtn.disabled = false;
        return true;
    }
}

// Расчет максимального количества воинов для выбранного корабля
function getMaxRowers() {
    const select = document.getElementById('shipSelect');
    const selectedOption = select.options[select.selectedIndex];
    return selectedOption ? parseInt(selectedOption.getAttribute('data-max-rowers')) : 0;
}

async function loadShips() {
    shipsData = await apiRequest('/api/ships');
    const select = document.getElementById('shipSelect');
    select.innerHTML = '<option value="">-- Select a ship --</option>';
    shipsData.forEach(ship => {
        const option = document.createElement('option');
        option.value = ship.id;
        option.setAttribute('data-max-rowers', ship.maxRowers);
        option.setAttribute('data-max-cargo', ship.maxCargo);
        option.setAttribute('data-max-slaves', ship.maxSlaves);
        option.setAttribute('data-base-speed', ship.baseSpeed);
        option.textContent = `${ship.name} (Max rowers: ${ship.maxRowers}, Cargo: ${ship.maxCargo})`;
        select.appendChild(option);
    });
    
    select.onchange = function() {
        const shipDesc = document.getElementById('shipDesc');
        if (this.value) {
            const ship = shipsData.find(s => s.id == this.value);
            shipDesc.innerHTML = `<strong>Description:</strong> ${ship.description}<br>
                                  <strong>Base speed:</strong> ${ship.baseSpeed} knots<br>
                                  <strong>Max rowers:</strong> ${ship.maxRowers}<br>
                                  <strong>Max cargo:</strong> ${ship.maxCargo}<br>
                                  <strong>Max slaves:</strong> ${ship.maxSlaves}`;
        } else {
            shipDesc.innerHTML = '';
        }
        checkCrewLimit();
        updateRouteInfo();
    };
}

async function loadCrew() {
    crewData = await apiRequest('/api/crew');
    const container = document.getElementById('crewList');
    container.innerHTML = '';
    
    crewData.forEach(member => {
        const div = document.createElement('div');
        div.className = 'crew-item';
        div.innerHTML = `
            <input type="checkbox" value="${member.id}" id="crew_${member.id}">
            <label for="crew_${member.id}">
                <strong>${member.name}</strong><br>
                Clan: ${member.clan} | Age: ${member.age}<br>
                Strength: ${member.strength} | Supplies: ${member.supplies}
            </label>
        `;
        const checkbox = div.querySelector('input');
        checkbox.onchange = function() {
            if (this.checked) {
                if (!selectedCrewIds.includes(member.id)) {
                    selectedCrewIds.push(member.id);
                }
            } else {
                selectedCrewIds = selectedCrewIds.filter(id => id !== member.id);
            }
            checkCrewLimit();
            updateSuppliesInfo();
            updateRouteInfo();
        };
        container.appendChild(div);
    });
}

function updateSuppliesInfo() {
    let totalSupplies = 0;
    selectedCrewIds.forEach(id => {
        const crew = crewData.find(c => c.id == id);
        if (crew) {
            totalSupplies += crew.supplies;
        }
    });
    
    const dailyConsumption = selectedCrewIds.length * 2;
    const daysOfFood = dailyConsumption > 0 ? Math.floor(totalSupplies / dailyConsumption) : 0;
    
    document.getElementById('totalSupplies').innerText = totalSupplies;
    const daysSpan = document.getElementById('daysOfFood');
    daysSpan.innerText = daysOfFood;
    
    if (daysOfFood < 10 && daysOfFood > 0) {
        daysSpan.style.color = '#ffaa00';
    } else if (daysOfFood === 0 && selectedCrewIds.length > 0) {
        daysSpan.style.color = '#ff6b6b';
    } else {
        daysSpan.style.color = '#c5d6e8';
    }
}

function updateRouteInfo() {
    if (routeIds.length === 0) {
        document.getElementById('totalDistance').innerText = '0';
        document.getElementById('estimatedDays').innerText = '0';
        return;
    }
    
    let totalDistance = 0;
    let prevX = 0, prevY = 0;
    
    routeIds.forEach(id => {
        const settlement = settlementsData.find(s => s.id == id);
        if (settlement) {
            const dist = calculateDistance(prevX, prevY, settlement.x, settlement.y);
            totalDistance += dist;
            prevX = settlement.x;
            prevY = settlement.y;
        }
    });
    
    const shipId = document.getElementById('shipSelect').value;
    let estimatedDays = 0;
    
    if (shipId && selectedCrewIds.length > 0) {
        const ship = shipsData.find(s => s.id == shipId);
        if (ship) {
            // Используем ПАРЫ гребцов для расчета скорости
            const maxPairs = Math.floor(ship.maxRowers / 2);
            const pairsCount = Math.floor(selectedCrewIds.length / 2);
            const ratio = maxPairs > 0 ? Math.min(1.0, pairsCount / maxPairs) : 0;
            const speed = ship.baseSpeed * ratio;
            estimatedDays = speed > 0 ? Math.ceil(totalDistance / speed) : 0;
        }
    }
    
    document.getElementById('totalDistance').innerText = totalDistance.toFixed(1);
    document.getElementById('estimatedDays').innerText = estimatedDays;
    
    const totalSupplies = selectedCrewIds.reduce((sum, id) => {
        const crew = crewData.find(c => c.id == id);
        return sum + (crew ? crew.supplies : 0);
    }, 0);
    const dailyConsumption = selectedCrewIds.length * 2;
    const daysOfFood = dailyConsumption > 0 ? Math.floor(totalSupplies / dailyConsumption) : 0;
    
    const daysSpan = document.getElementById('estimatedDays');
    if (estimatedDays > daysOfFood && daysOfFood > 0) {
        daysSpan.style.color = '#ffaa00';
        daysSpan.title = `Warning: Not enough supplies! Need ${estimatedDays} days but only ${daysOfFood} days of food.`;
    } else if (estimatedDays > 0 && daysOfFood === 0) {
        daysSpan.style.color = '#ff6b6b';
    } else {
        daysSpan.style.color = '#c5d6e8';
    }
}

async function loadSettlements() {
    settlementsData = await apiRequest('/api/settlements');
    const container = document.getElementById('settlementsList');
    container.innerHTML = '';
    
    settlementsData.forEach(settlement => {
        const distance = calculateDistance(0, 0, settlement.x, settlement.y);
        const div = document.createElement('div');
        div.className = 'settlement-item';
        div.innerHTML = `
            <div>
                <strong>${settlement.name}</strong> (${settlement.type})<br>
                Scale: ${settlement.scale} | Loot: ${settlement.baseLoot}<br>
                📏 Distance: ${distance.toFixed(1)} units<br>
                <small>${settlement.description || ''}</small>
            </div>
        `;
        div.onclick = () => {
            if (!routeIds.includes(settlement.id)) {
                routeIds.push(settlement.id);
                renderRoute();
                updateRouteInfo();
            }
        };
        container.appendChild(div);
    });
}

function renderRoute() {
    const container = document.getElementById('routeList');
    container.innerHTML = '';
    
    let cumulative = 0;
    let prevX = 0, prevY = 0;
    
    routeIds.forEach((id, index) => {
        const settlement = settlementsData.find(s => s.id == id);
        if (settlement) {
            const dist = calculateDistance(prevX, prevY, settlement.x, settlement.y);
            cumulative += dist;
            
            const li = document.createElement('li');
            li.innerHTML = `
                ${index + 1}. ${settlement.name} (${dist.toFixed(1)} units)
                <button class="remove-route" data-index="${index}">✖</button>
            `;
            container.appendChild(li);
            
            prevX = settlement.x;
            prevY = settlement.y;
        }
    });
    
    document.querySelectorAll('.remove-route').forEach(btn => {
        btn.onclick = function() {
            const index = parseInt(this.dataset.index);
            routeIds.splice(index, 1);
            renderRoute();
            updateRouteInfo();
        };
    });
    
    if (routeIds.length > 0) {
        const totalDist = document.createElement('li');
        totalDist.style.background = 'rgba(15, 25, 40, 0.7)';
        totalDist.style.marginTop = '10px';
        totalDist.style.borderLeft = '3px solid #7eb8da';
        totalDist.style.color = '#c5d6e8';
        totalDist.innerHTML = `<strong>Total: ${cumulative.toFixed(1)} units</strong>`;
        container.appendChild(totalDist);
    }
}

async function startSimulation() {
    const shipId = document.getElementById('shipSelect').value;
    if (!shipId) { alert('Select a ship!'); return; }
    if (selectedCrewIds.length === 0) { alert('Select at least one warrior!'); return; }
    if (routeIds.length === 0) { alert('Select at least one settlement!'); return; }
    
    // Проверка лимита перед отправкой
    const maxRowers = getMaxRowers();
    if (selectedCrewIds.length > maxRowers) {
        alert(`This ship can only carry ${maxRowers} warriors! You selected ${selectedCrewIds.length}.`);
        return;
    }
    
    try {
        const expedition = await apiRequest('/api/expeditions', 'POST', {
            shipId: parseInt(shipId),
            crewIds: selectedCrewIds,
            settlementIds: routeIds
        });
        
        const result = await apiRequest(`/api/expeditions/${expedition.id}/simulate`, 'POST');
        
        const resultCard = document.getElementById('resultCard');
        const resultContent = document.getElementById('resultContent');
        resultCard.style.display = 'block';
        
        if (result.feasible) {
            let lootText = 'No loot';
            if (result.lootByType && Object.keys(result.lootByType).length > 0) {
                lootText = Object.entries(result.lootByType).map(([k, v]) => `  ${k}: ${v}`).join('\n');
            }
            
            resultContent.innerText = `✅ RAID SUCCESSFUL!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
📅 Total days: ${result.totalDays}
📏 Total distance: ${(result.totalDistance || 0).toFixed(1)} units
💰 Total loot value: ${result.totalLootValue}
👥 Slaves captured: ${result.totalSlaves}

📊 Loot breakdown:
${lootText}`;
        } else {
            resultContent.innerText = `❌ RAID FAILED!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Reason: ${result.failureReason}`;
        }
        
        loadHistory();
    } catch (error) {
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
                Crew: ${h.crewCount || 0} | Settlements: ${h.settlementCount || 0}<br>
                Days: ${h.totalDays || 0} | Loot: ${h.totalLoot || 0} | Slaves: ${h.totalSlaves || 0}
                ${h.failureReason ? `<br>❌ ${h.failureReason}` : ''}
            </div>
        `).join('');
    } catch (e) {
        console.error('Failed to load history:', e);
    }
}

function logout() {
    currentUser = null;
    selectedCrewIds = [];
    routeIds = [];
    shipsData = [];
    settlementsData = [];
    crewData = [];
    
    document.getElementById('mainContent').style.display = 'none';
    document.getElementById('authSection').style.display = 'block';
    
    document.getElementById('loginUsername').value = '';
    document.getElementById('loginPassword').value = '';
}

async function login(username, password) {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    
    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
    });
    if (!response.ok) throw new Error('Registration failed');
    return response.json();
}

document.getElementById('logoutBtn').onclick = logout;

document.getElementById('loginBtn').onclick = async () => {
    try {
        await login(document.getElementById('loginUsername').value, document.getElementById('loginPassword').value);
        document.getElementById('authSection').style.display = 'none';
        document.getElementById('mainContent').style.display = 'block';
        loadShips();
        loadCrew();
        loadSettlements();
        loadHistory();
    } catch (e) {
        alert('Login failed: ' + e.message);
    }
};

document.getElementById('registerBtn').onclick = async () => {
    try {
        await register(document.getElementById('regUsername').value, document.getElementById('regPassword').value);
        alert('Registration successful! You can now login.');
    } catch (e) {
        alert('Registration failed: ' + e.message);
    }
};

document.getElementById('simulateBtn').onclick = startSimulation;