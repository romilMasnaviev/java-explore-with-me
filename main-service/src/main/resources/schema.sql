DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id INT GENERATED BY DEFAULT AS IDENTITY,
    name varchar(250),
    email varchar(254) unique
);