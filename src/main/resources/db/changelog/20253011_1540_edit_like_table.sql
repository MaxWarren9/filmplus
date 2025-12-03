CREATE TABLE IF NOT EXISTS filmplus.like
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    BIGINT
    NOT
    NULL
    references
    filmplus
    .
    user
(
    id
),
    film_id BIGINT NOT NULL references filmplus.film
(
    id
),
    created_at TIMESTAMP NOT NULL
    );