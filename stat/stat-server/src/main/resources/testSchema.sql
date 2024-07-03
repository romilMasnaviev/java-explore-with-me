DROP TABLE IF EXISTS stat_entity;

create table if not exists stat_entity
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app       varchar(50),
    uri       varchar(50),
    ip        varchar(20),
    timestamp timestamp
)