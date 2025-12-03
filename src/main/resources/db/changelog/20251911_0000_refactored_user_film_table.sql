CREATE TABLE IF NOT EXISTS filmplus.film
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    release     DATE    NOT NULL,
    duration    BIGINT  NOT NULL,
    genres      VARCHAR NOT NULL
);