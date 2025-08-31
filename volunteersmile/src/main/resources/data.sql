-- data.sql
-- Popula dados iniciais para ambiente local (PostgreSQL)
-- Observação: spring.jpa.hibernate.ddl-auto=create recriará o schema a cada start, então estes inserts serão aplicados após o Hibernate gerar as tabelas.
-- Caso passe a usar validate/update em produção, considere separar schema.sql (DDL) de data.sql (DML).

-- Limpando tabela (útil se estiver usando ddl-auto=update). Comentado por padrão.
-- TRUNCATE TABLE users CASCADE;
-- TRUNCATE TABLE rooms CASCADE;

-- Observação nova: coluna app_role adicionada à entidade User para evitar reflexão do discriminador.
-- Para bases já existentes, executar (exemplo):
-- ALTER TABLE users ADD COLUMN app_role VARCHAR(30);
-- UPDATE users SET app_role = role WHERE app_role IS NULL;
-- ALTER TABLE users ALTER COLUMN app_role SET NOT NULL;

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
INSERT INTO rooms (id, floor, number, difficulty_level, priority)
VALUES
  ('10000000-0000-0000-0000-000000000001', 1, 101, 1, 'LOW'),
  ('10000000-0000-0000-0000-000000000002', 2, 205, 3, 'MEDIUM'),
  ('10000000-0000-0000-0000-000000000003', 3, 309, 4, 'HIGH');

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
