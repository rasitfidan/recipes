CREATE TABLE Recipe (
    id integer GENERATED ALWAYS AS IDENTITY AUTO_INCREMENT NOT NULL,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    vegetarian BOOLEAN NOT NULL,
    serving_for integer NOT NULL,
    CONSTRAINT pk_recipe PRIMARY KEY (id)
);

CREATE TABLE Ingredient (
    id integer GENERATED ALWAYS AS IDENTITY AUTO_INCREMENT NOT NULL,
    recipe_id integer NOT NULL,
    item_name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    amount REAL NOT NULL,
    amount_type ENUM('PIECE','GRAM','KILOGRAM','DOZEN','OUNCE','POUND','TABLESPOON','TEASPOON','GLASS','LITER','CUP') NOT NULL,
    CONSTRAINT pk_ingredient PRIMARY KEY (id),
    CONSTRAINT fk_ingredient_recipe FOREIGN KEY (recipe_id)
        REFERENCES Recipe (id)
);

CREATE TABLE Instruction (
    id integer GENERATED ALWAYS AS IDENTITY AUTO_INCREMENT NOT NULL,
    recipe_id integer NOT NULL,
    description VARCHAR NOT NULL,
    sequence integer NOT NULL,
    CONSTRAINT pk_instruction PRIMARY KEY (id),
    CONSTRAINT fk_instruction_recipe FOREIGN KEY (recipe_id)
        REFERENCES Recipe (id)
);