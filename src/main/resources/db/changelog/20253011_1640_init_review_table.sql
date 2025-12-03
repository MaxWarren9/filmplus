CREATE TABLE IF NOT EXISTS filmplus.review
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
    rate BIGINT NOT NULL CHECK
(
    rate
    IN
(
    1,
    2,
    3,
    4,
    5
)),
    review VARCHAR
(
    255
) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at DATE
    );