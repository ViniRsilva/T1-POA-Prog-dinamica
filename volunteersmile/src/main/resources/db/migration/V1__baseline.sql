-- Flyway baseline migration
-- This script represents the current schema structure. Adjust as needed to match actual DB.

-- USERS table (single table inheritance for users/volunteers)
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    app_role VARCHAR(30) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(30),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    last_login TIMESTAMP,
    room_access_level INTEGER,
    description_voluntary TEXT
);

-- ROOMS table (example, adjust if differs in real schema)
CREATE TABLE IF NOT EXISTS rooms (
    id UUID PRIMARY KEY,
    floor INTEGER NOT NULL,
    number INTEGER NOT NULL,
    difficulty_level INTEGER NOT NULL,
    priority VARCHAR(20) NOT NULL
);

-- VISITS table
CREATE TABLE IF NOT EXISTS visits (
    id UUID PRIMARY KEY,
    id_room UUID NOT NULL REFERENCES rooms(id),
    start_date DATE,
    end_date DATE,
    scheduling_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    duration_minutes INTEGER,
    notes TEXT
);

-- USERS_VISITS relation
CREATE TABLE IF NOT EXISTS users_visits (
    id UUID PRIMARY KEY,
    id_user UUID NOT NULL REFERENCES users(id),
    id_visit UUID NOT NULL REFERENCES visits(id),
    volunteer_feedback TEXT,
    attendance_confirmed BOOLEAN
);

-- Indexes (add as needed)
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_visits_room ON visits(id_room);
CREATE INDEX IF NOT EXISTS idx_users_visits_user ON users_visits(id_user);
CREATE INDEX IF NOT EXISTS idx_users_visits_visit ON users_visits(id_visit);
