-- data.sql
-- Popula dados iniciais para ambiente local (PostgreSQL)
-- Este arquivo é executado no profile "local". O schema é gerenciado pelo Flyway (V1__create_tables.sql).
-- Em application-local.properties: spring.sql.init.mode=always e spring.jpa.defer-datasource-initialization=true

-- Reset dos dados dummy (idempotente): remove os registros pelos IDs antes de inserir novamente
-- Ordem importa por causa de FKs: users_visits -> visits -> users -> rooms
DELETE FROM users_visits WHERE id IN (
    '30000000-0000-0000-0000-000000000001',
    '30000000-0000-0000-0000-000000000002',
    '30000000-0000-0000-0000-000000000003'
);

DELETE FROM visits WHERE id IN (
    '20000000-0000-0000-0000-000000000001',
    '20000000-0000-0000-0000-000000000002',
    '20000000-0000-0000-0000-000000000003'
);

DELETE FROM users WHERE id IN (
    '00000000-0000-0000-0000-000000000001', -- admin
    '00000000-0000-0000-0000-000000000101', -- joao
    '00000000-0000-0000-0000-000000000102'  -- maria
);

DELETE FROM rooms WHERE id IN (
    '10000000-0000-0000-0000-000000000001',
    '10000000-0000-0000-0000-000000000002',
    '10000000-0000-0000-0000-000000000003'
);

-- Limpando tabela (útil se estiver usando ddl-auto=update). Comentado por padrão.
-- TRUNCATE TABLE users CASCADE;
-- TRUNCATE TABLE rooms CASCADE;

-- Observação: a tabela users usa Single Table Inheritance com discriminador "role" e campo obrigatório "app_role" (enum UserRole).

-- Inserindo um ADMIN (inclui app_role)
INSERT INTO users (id, role, app_role, email, password, name, phone_number, status, created_at, deleted_at, last_login, room_access_level, description_voluntary)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'ADMIN',
    'ADMIN',
    'admin@volunteersmile.com',
    -- senha: Admin123!
    '$2a$10$wO3eGhnAE8rumVn6dZPw.eI0/fWznuaCG2lLBVmOAXoJ1Bs8LojF6',
    'Administrador',
    '+55 11 90000-0000',
    'ACTIVE',
    NOW(),
    NULL,
    NULL,
    NULL,
    NULL
);

-- Inserindo VOLUNTEERS (inclui app_role)
INSERT INTO users (id, role, app_role, email, password, name, phone_number, status, created_at, deleted_at, last_login, room_access_level, description_voluntary)
VALUES (
    '00000000-0000-0000-0000-000000000101',
    'VOLUNTEER',
    'VOLUNTEER',
    'joao.silva@volunteersmile.com',
    -- senha: Voluntario123!
    '$2a$10$HtZpZaZX6mE6xPD58Ll35.f.xYzUPrK1V.VYqVQXHhj6nQSLr9EZW',
    'João Silva',
    '+55 11 98888-1111',
    'PENDING',
    NOW(),
    NULL,
    NULL,
    2,
    'Voluntário focado em logística.'
), (
    '00000000-0000-0000-0000-000000000102',
    'VOLUNTEER',
    'VOLUNTEER',
    'maria.oliveira@volunteersmile.com',
    -- senha: Voluntaria123!
    '$2a$10$O0W1S49AmDNpfilqwYjzepnJDsLotfjL7vQfAlWTMwVzKhI6.wK.2',
    'Maria Oliveira',
    '+55 11 97777-2222',
    'ACTIVE',
        NOW(),
    NULL,
    NULL,
    3,
    'Experiência em organização de eventos.'
);

-- Inserindo salas (rooms)
-- Atenção: pela migration V1, rooms possui colunas NOT NULL extras: sector, max_occupancy, status, description, priority
INSERT INTO rooms (id, floor, number, difficulty_level, sector, max_occupancy, status, description, priority)
VALUES
    ('10000000-0000-0000-0000-000000000001', 1, 101, 1, 'A', 4, 'AVAILABLE', 'Quarto térreo próximo à recepção.', 'LOW'),
    ('10000000-0000-0000-0000-000000000002', 2, 205, 3, 'B', 6, 'AVAILABLE', 'Sala no segundo andar, ala B.', 'MEDIUM'),
    ('10000000-0000-0000-0000-000000000003', 3, 309, 4, 'C', 2, 'MAINTENANCE', 'Sala em manutenção leve.', 'HIGH');

-- Inserindo visitas (visits)
-- Campos: id, id_room (FK), start_date, end_date, scheduling_date, status, duration_minutes, notes
INSERT INTO visits (id, id_room, start_date, end_date, scheduling_date, status, duration_minutes, notes) VALUES
        ('20000000-0000-0000-0000-000000000001', '10000000-0000-0000-0000-000000000001', CURRENT_DATE + INTERVAL '1 day', NULL, NOW(), 'SCHEDULED', NULL, 'Visita agendada para avaliação inicial.'),
        ('20000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000002', CURRENT_DATE - INTERVAL '3 day', CURRENT_DATE - INTERVAL '3 day', NOW() - INTERVAL '5 day', 'COMPLETED', 90, 'Visita concluída com melhorias implementadas.'),
        ('20000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000003', CURRENT_DATE + INTERVAL '5 day', NULL, NOW() - INTERVAL '1 day', 'SCHEDULED', NULL, 'Aguardando confirmação de participantes.');

-- Relacionamentos users_visits
-- Campos: id, id_user, id_visit, volunteer_feedback, attendance_confirmed
INSERT INTO users_visits (id, id_user, id_visit, volunteer_feedback, attendance_confirmed) VALUES
    ('30000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000101', '20000000-0000-0000-0000-000000000002', 'Cheguei no horário e executei as tarefas conforme combinado.', TRUE),
    ('30000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000102', '20000000-0000-0000-0000-000000000002', 'Boa sinergia com a equipe, podemos otimizar o fluxo.', TRUE),
    ('30000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000101', '20000000-0000-0000-0000-000000000001', NULL, NULL);
