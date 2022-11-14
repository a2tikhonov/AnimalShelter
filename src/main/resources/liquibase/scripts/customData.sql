-- liquibase formatted sql

-- changeSet: ydeev:1

-- creating table Owner

CREATE TABLE IF NOT EXISTS owners
(
    id SERIAL PRIMARY KEY,
-- chat_id integer
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(20) NOT NULL,
    age integer not NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    has_adopted_animal BOOLEAN DEFAULT (FALSE) NOT NULL
);

-- ALTER TABLE owner ADD CONSTRAINT phone_number_constraint
--     CHECK (phone_number REGEXP '[+]?[0-9]{1,3} ?\\(?[0-9]{3}\\)? ?[0-9]{2}[0-9 -]+[0-9]{2}');
-- creating table Pet

CREATE TABLE IF NOT EXISTS pets
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    weight NUMERIC NOT NULL,
    age integer NOT NULL,
    breed VARCHAR(30),
    is_adopted BOOLEAN DEFAULT (FALSE) NOT NULL,
    owner_id REAL REFERENCES owners (id)
);

-- add some data into tables

INSERT INTO shelter(shelter_id, description, email, location)
VALUES (1, 'Jd 6/team № 3 Shelter', 'Jd6_t3_shelter@gmail.com', 'Come on, use Google... meh...');

INSERT INTO owners (phone_number, name, age, email)
VALUES
    (+7-985-445-72-80, 'Mark', 19, 'MarkGoebel@gmail.com'),
    (+7-916-390-42-37, 'Dmitriy', 23, 'dmitriy_topol99@gmail.com'),
    (+7-916-238-89-13, 'Ann', 26, 'ann_sinicina@gmail.com'),
    (+7-985-536-19-21, 'Darya', 24, 'daryabelova_548@gmail.com');

INSERT INTO pets (name, weight, age, breed, adopted)
VALUES
    ('Landish', 2.7, 1, 'persian', false),
    ('Busya', 3.4, 3, 'foldex', false),
    ('Khadjo', 73, 21, 'khajiit', false),
    ('Neltarion', 32, 4, 'german shepherd', false),
    ('Maligos', 16.2, 2, 'border collie', false),
    ('Diktator', 15, 5 ,'free breed dog', false)

