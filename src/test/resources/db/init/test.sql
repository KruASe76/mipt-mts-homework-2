CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    age INTEGER NOT NULL,
    is_mipt BOOLEAN NOT NULL CHECK ( is_mipt = true ),
    is_sleeping BOOLEAN NOT NULL CHECK ( is_sleeping = false )
);

CREATE TABLE programming_languages (
    name VARCHAR(256) NOT NULL PRIMARY KEY,
    version VARCHAR(64) NOT NULL,
    top_n_popular INTEGER NOT NULL check ( top_n_popular >= 1 ),
    is_compiled BOOLEAN NOT NULL,
    is_strictly_typed BOOLEAN NOT NULL
)
