CREATE TABLE room_feedbacks
(
    id          UUID         NOT NULL,
    user_name   VARCHAR(255) NOT NULL,
    date        date,
    feedback    TEXT,
    floor       INTEGER,
    room_number INTEGER,
    room_id     UUID         NOT NULL,
    CONSTRAINT pk_room_feedbacks PRIMARY KEY (id)
);

CREATE TABLE rooms
(
    id               UUID         NOT NULL,
    floor            INTEGER      NOT NULL,
    number           INTEGER      NOT NULL,
    difficulty_level INTEGER      NOT NULL,
    sector           VARCHAR(255) NOT NULL,
    max_occupancy    INTEGER      NOT NULL,
    status           VARCHAR(255) NOT NULL,
    description      TEXT,
    priority         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_rooms PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                    UUID         NOT NULL,
    role                  VARCHAR(31)  NOT NULL,
    email                 VARCHAR(150) NOT NULL,
    password              VARCHAR(255) NOT NULL,
    name                  VARCHAR(255) NOT NULL,
    phone_number          VARCHAR(30),
    status                VARCHAR(20)  NOT NULL,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    last_login            TIMESTAMP WITHOUT TIME ZONE,
    app_role              VARCHAR(30)  NOT NULL,
    room_access_level     INTEGER,
    description_voluntary TEXT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_visits
(
    id                   UUID NOT NULL,
    id_user              UUID NOT NULL,
    id_visit             UUID NOT NULL,
    volunteer_feedback   TEXT,
    attendance_confirmed BOOLEAN,
    CONSTRAINT pk_users_visits PRIMARY KEY (id)
);

CREATE TABLE visits
(
    id               UUID         NOT NULL,
    id_room          UUID         NOT NULL,
    start_date       date         NOT NULL,
    end_date         date,
    scheduling_date  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status           VARCHAR(255) NOT NULL,
    duration_minutes INTEGER,
    notes            TEXT,
    CONSTRAINT pk_visits PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE room_feedbacks
    ADD CONSTRAINT FK_ROOM_FEEDBACKS_ON_ROOM FOREIGN KEY (room_id) REFERENCES rooms (id);

ALTER TABLE users_visits
    ADD CONSTRAINT FK_USERS_VISITS_ON_ID_USER FOREIGN KEY (id_user) REFERENCES users (id);

ALTER TABLE users_visits
    ADD CONSTRAINT FK_USERS_VISITS_ON_ID_VISIT FOREIGN KEY (id_visit) REFERENCES visits (id);

ALTER TABLE visits
    ADD CONSTRAINT FK_VISITS_ON_ID_ROOM FOREIGN KEY (id_room) REFERENCES rooms (id);