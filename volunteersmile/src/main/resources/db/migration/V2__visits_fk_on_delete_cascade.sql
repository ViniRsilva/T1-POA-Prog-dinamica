-- V2__visits_fk_on_delete_cascade.sql
-- Altera a constraint FK de visits -> rooms para ON DELETE CASCADE

ALTER TABLE visits DROP CONSTRAINT IF EXISTS fk_visits_on_id_room;
ALTER TABLE visits ADD CONSTRAINT fk_visits_on_id_room FOREIGN KEY (id_room) REFERENCES rooms(id) ON DELETE CASCADE;
