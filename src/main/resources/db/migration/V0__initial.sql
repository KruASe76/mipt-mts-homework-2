CREATE TABLE users
(
    id    BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    name  VARCHAR(255) NOT NULL,
    age   INTEGER      NOT NULL
);

CREATE INDEX idx_users_email ON users (email);


CREATE TABLE universities
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    location TEXT         NOT NULL
);


CREATE TABLE courses
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    credits       INTEGER      NOT NULL,
    university_id BIGINT       NOT NULL REFERENCES universities (id) ON DELETE CASCADE
);


CREATE TABLE books
(
    id        BIGSERIAL PRIMARY KEY,
    title     TEXT         NOT NULL,
    author    VARCHAR(255) NOT NULL,
    course_id BIGINT       NOT NULL REFERENCES courses (id) ON DELETE CASCADE
);
