CREATE TABLE auto_post
(
    id           serial PRIMARY KEY,
    description  text,
    created      timestamp NOT NULL,
    auto_user_id bigint REFERENCES auto_user (id)
);