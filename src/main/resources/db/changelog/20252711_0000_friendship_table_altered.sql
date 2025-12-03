CREATE TABLE IF NOT EXISTS filmplus.friendship
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
    friend_id BIGINT NOT NULL references filmplus.user
(
    id
),
    status VARCHAR NOT NULL,
    created TIMESTAMP NOT NULL,
    updated DATE
    );