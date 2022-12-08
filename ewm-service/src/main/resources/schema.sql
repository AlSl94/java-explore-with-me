CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name  VARCHAR(50)                         NOT NULL,
    email VARCHAR(90)                         NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_name UNIQUE (name),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY,
    title  VARCHAR(80) NOT NULL,
    pinned BOOLEAN     NOT NULL,
    CONSTRAINT pk_compilation PRIMARY KEY (id),
    CONSTRAINT uq_compilation_title UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY,
    annotation         VARCHAR(1000)               NOT NULL,
    description        VARCHAR(8000)               NOT NULL,
    confirmed_requests INT                         NOT NULL,
    category_id        BIGINT
        CONSTRAINT fk_events_categories_index REFERENCES categories (id) ON DELETE CASCADE,
    title              VARCHAR(200)                NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id       BIGINT                      NOT NULL
        CONSTRAINT fk_events_users_index REFERENCES users (id) ON DELETE CASCADE,
    lat                FLOAT                       NOT NULL,
    lon                FLOAT                       NOT NULL,
    paid               BOOLEAN                     NOT NULL,
    participant_limit  INT                         NOT NULL,
    request_moderation BOOLEAN                     NOT NULL,
    state              VARCHAR(40)                 NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events_likes
(
    user_id  BIGINT NOT NULL
        CONSTRAINT fk_events_likes_users_index REFERENCES users (id) ON DELETE CASCADE,
    event_id BIGINT NOT NULL
        CONSTRAINT fk_events_likes_events_index REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT pk_events_users PRIMARY KEY (user_id, event_id)
);

CREATE TABLE IF NOT EXISTS events_dislikes
(
    user_id  BIGINT NOT NULL
        CONSTRAINT fk_events_dislikes_users_index REFERENCES users (id) ON DELETE CASCADE,
    event_id BIGINT NOT NULL
        CONSTRAINT fk_events_dislikes_events_index REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT pk_events_users_dislikes PRIMARY KEY (user_id, event_id)
);


CREATE TABLE IF NOT EXISTS events_compilations
(
    compilation_id BIGINT NOT NULL
        CONSTRAINT fk_events_comps_compilations_index REFERENCES compilations (id) ON DELETE CASCADE,
    event_id       BIGINT NOT NULL
        CONSTRAINT fk_events_comps_events_index REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT pk_events_compilations PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id     BIGINT                      NOT NULL
        CONSTRAINT fk_requests_events_index REFERENCES events (id) ON DELETE CASCADE,
    requester_id BIGINT                      NOT NULL
        CONSTRAINT fk_requests_users_index REFERENCES users (id) ON DELETE CASCADE,
    status       varchar(50)                 NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (id)
);