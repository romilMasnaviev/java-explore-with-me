DROP TABLE IF EXISTS StatEntity;

CREATE TABLE IF NOT EXISTS stat_entity
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    app varchar(50),
    uri varchar(50),
    ip varchar(20),
    timestamp timestamp,
    hits int
);