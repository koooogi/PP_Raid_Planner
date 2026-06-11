-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);

-- Таблица кораблей
CREATE TABLE IF NOT EXISTS viking_ships (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    max_rowers INT DEFAULT 10,
    max_cargo INT DEFAULT 8000,
    max_slaves INT DEFAULT 20,
    base_speed INT DEFAULT 10,
    food_consumption_per_person INT DEFAULT 2,
    description TEXT
);

-- Таблица членов экипажа
CREATE TABLE IF NOT EXISTS crew_members (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    clan VARCHAR(100),
    gender VARCHAR(10),
    age INT,
    strength INT DEFAULT 10,
    supplies INT DEFAULT 30
);

-- Таблица поселений
CREATE TABLE IF NOT EXISTS settlements (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20),
    x DOUBLE PRECISION,
    y DOUBLE PRECISION,
    scale INT,
    base_loot INT DEFAULT 1000,
    slave_probability DOUBLE PRECISION DEFAULT 0.3,
    min_slaves INT DEFAULT 0,
    max_slaves INT DEFAULT 10,
    description TEXT
);

-- Таблица экспедиций (походов)
CREATE TABLE IF NOT EXISTS expeditions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    ship_id BIGINT REFERENCES viking_ships(id),
    crew_ids TEXT,
    route TEXT,
    status VARCHAR(20),
    simulation_params TEXT,
    simulation_result TEXT,
    total_days INT,
    total_loot INT,
    total_slaves INT,
    failure_reason TEXT
);

-- Экспедиция 1: список воинов с именами
SELECT 
    e.id AS expedition_id,
    cm.name AS warrior_name,
    cm.clan,
    cm.strength
FROM expeditions e
CROSS JOIN LATERAL jsonb_array_elements_text(e.crew_ids::jsonb) AS warrior_id
JOIN crew_members cm ON cm.id = warrior_id::bigint
WHERE e.id = 1
ORDER BY warrior_id::bigint;

-- Экспедиция 1: список поселений в порядке посещения
SELECT 
    e.id AS expedition_id,
    s.name AS settlement_name,
    s.type AS settlement_type,
    s.scale AS difficulty,
    s.base_loot,
    ROW_NUMBER() OVER (ORDER BY order_index) AS visit_order
FROM expeditions e
CROSS JOIN LATERAL jsonb_array_elements_text(e.route::jsonb) WITH ORDINALITY AS route(settlement_id, order_index)
JOIN settlements s ON s.id = route.settlement_id::bigint
WHERE e.id = 1
ORDER BY route.order_index;