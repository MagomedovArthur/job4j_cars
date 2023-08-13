CREATE TABLE auto_user
(
    id       serial PRIMARY KEY,
    login    varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);