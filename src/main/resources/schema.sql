DROP TABLE IF EXISTS recipe;

CREATE TABLE recipe (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    food_type VARCHAR(255) NOT NULL,
    servings INT,
    cooking_time INT,
    ingredients TEXT,
    instructions TEXT,
    image VARCHAR(255)
);