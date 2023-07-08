DROP TABLE IF EXISTS events, categories, locations, users, requests, compilations, compilations_events CASCADE;

CREATE TABLE IF NOT EXISTS categories
(
    id   INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(1024)                        NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uq_categories_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat FLOAT                                NOT NULL,
    lon FLOAT                                NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id    INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email VARCHAR(1024)                        NOT NULL,
    name  VARCHAR(1024)                        NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                        NOT NULL,
    category_id        INT                                  NOT NULL,
    confirmed_requests INT                                  NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(7000)                        NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    initiator_id       INT                                  NOT NULL,
    location_id        INT                                  NOT NULL,
    paid               BOOLEAN                              NOT NULL,
    participant_limit  INT                                  NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                              NOT NULL,
    state              VARCHAR(50)                        NOT NULL,
    title              VARCHAR(120)                        NOT NULL,
    views              INT                                  NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT fk_categories FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_initiator FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES locations (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    event_id     INT                                  NOT NULL,
    requester_id INT                                  NOT NULL,
    status       VARCHAR(50)                          NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_requester FOREIGN KEY (requester_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                              NOT NULL,
    title  VARCHAR(1024)                        NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilations_id INT NOT NULL,
    events_id       INT NOT NULL,
    CONSTRAINT fk_supportive_compilation FOREIGN KEY (compilations_id) references compilations (id),
    CONSTRAINT fk_events FOREIGN KEY (events_id) REFERENCES events (id)
);