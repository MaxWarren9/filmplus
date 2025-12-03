CREATE SCHEMA IF NOT EXISTS filmplus;

CREATE TABLE IF NOT EXISTS filmplus.user
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR        NOT NULL,
    name     VARCHAR        NOT NULL,
    email    VARCHAR UNIQUE NOT NULL,
    birthday DATE
);

