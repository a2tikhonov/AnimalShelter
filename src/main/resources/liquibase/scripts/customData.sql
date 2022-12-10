-- liquibase formatted sql

-- changeSet: ydeev:1

-- creating table cynologists

CREATE TABLE IF NOT EXISTS cynologists
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(20)        NOT NULL,
    phone_number      VARCHAR(20) UNIQUE NOT NULL,
    specialties       TEXT               NOT NULL,
    years_of_practice DECIMAL            NOT NULL
);

-- creating table locations

CREATE TABLE IF NOT EXISTS locations
(
    id         SERIAL PRIMARY KEY,
    file_path  TEXT UNIQUE,
    file_size  BIGINT,
    mediaType  TEXT,
    data       bytea,
    shelter_id SERIAL REFERENCES shelters (id)
);

-- creating table main_menu

CREATE TABLE IF NOT EXISTS main_menu
(
    id        SERIAL PRIMARY KEY,
    button    TEXT UNIQUE NOT NULL,
    call_back TEXT UNIQUE NOT NULL
);

-- creating table new_user_menu

CREATE TABLE IF NOT EXISTS new_user_menu
(
    id        SERIAL PRIMARY KEY,
    button    TEXT UNIQUE NOT NULL,
    call_back TEXT UNIQUE NOT NULL
);

-- creating table pets

CREATE TABLE IF NOT EXISTS pets
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    weight             BIGINT      NOT NULL,
    age                BIGINT      NOT NULL,
    breed              VARCHAR(30),
    species            VARCHAR(30),
    disabled           BOOLEAN DEFAULT FALSE,
    potential_owner_id REAL REFERENCES potentialOwners (id),
    shelter_id         REAL REFERENCES shelters (id)
);

-- creating table potentialOwners

CREATE TABLE IF NOT EXISTS potentialOwners
(
    id           SERIAL PRIMARY KEY NOT NULL,
--     chat_id      integer UNIQUE     NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name         VARCHAR(20)        NOT NULL,
    volunteer_id REAL REFERENCES volunteers (id)
);

-- creating table potential_owner_menu

CREATE TABLE IF NOT EXISTS potential_owner_menu
(
    id        SERIAL PRIMARY KEY NOT NULL,
    button    TEXT UNIQUE        NOT NULL,
    call_back TEXT UNIQUE        NOT NULL
);

-- creating table recommendations

-- CREATE TABLE IF NOT EXISTS recommendations
-- (
--     id                  SERIAL PRIMARY KEY NOT NULL,
--     rules               TEXT,
--     documents           TEXT,
--     transportation_info TEXT,
--     arrangement_info    TEXT,
--     cynologist_advices  TEXT,
--     cynologists         TEXT,
--     common_rejects      TEXT
-- );

-- creating table reports

CREATE TABLE IF NOT EXISTS reports
(
    id                 SERIAL PRIMARY KEY,
    file_path          TEXT,
    file_size          BIGINT,
    photo              BYTEA,
    date_time          TIMESTAMP WITHOUT TIME ZONE,
    condition          TEXT,
    diet               TEXT,
    changes            TEXT,
    mediaType          TEXT,
    potential_owner_id REAL REFERENCES potentialOwners (id)
);

-- creating table shelter

CREATE TABLE IF NOT EXISTS shelters
(
    id             SERIAL PRIMARY KEY,
    location       TEXT UNIQUE        NOT NULL,
    description    TEXT               NOT NULL,
    email          VARCHAR(30) UNIQUE NOT NULL,
    how_to_find_us TEXT               NOT NULL
);


-- creating table volunteers

CREATE TABLE IF NOT EXISTS volunteers
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(20)        NOT NULL,
    phone_number       VARCHAR(20) UNIQUE NOT NULL,
    email              VARCHAR(30) UNIQUE NOT NULL,
    busy               BOOLEAN DEFAULT FALSE,
    shelter_id         REAL REFERENCES shelters (id),
    potential_owner_id REAL REFERENCES potentialOwners (id)
);

-- changeSet: ydeev:2

-- add some data into table cynologists

INSERT INTO cynologists
    (name, phone_number, specialties, years_of_practice)
VALUES ('Baron', 89725551980, 'dog_master', 6),
       ('Blake', 89187831520, 'cat_master', 4),
       ('Amanda', 86127382460, 'dog_and_cat_master', 8),
       ('Tyrion', 88329651730, 'beginner', 1),
       ('Denice', 89856129017, 'adept', 10);

-- add some data into table locations

INSERT INTO locations
    (file_path, file_size, mediaType, data)
VALUES ('aaa', null, null, null),
       ('bbb', null, null, null);

-- add some data into table main_menu

INSERT INTO main_menu
    (button, call_back)
VALUES ('Добро пожаловать', 'Добро пожаловать'),
       ('Добро пожаловать', 'Добро пожаловать'),
       ('Добро пожаловать', 'Добро пожаловать');

-- add some data into table pets

INSERT INTO pets
(name, weight, age, breed, species, disabled, potential_owner_id, shelter_id)
VALUES ('Landish', 2.7, 1, 'persian', 'cat', false, null, null),
       ('Busya', 3.4, 3, 'foldex', 'cat', false, null, null),
       ('Khadjo', 73, 21, 'khajiit', 'cat', false, null, null),
       ('Neltarion', 32, 4, 'german shepherd', 'dog', false, null, null),
       ('Maligos', 16.2, 2, 'border collie', 'dog', false, null, null),
       ('Diktator', 15, 5, 'free breed dog', 'dog', false, null, null);

-- add some data into table potentional_owners

INSERT INTO potentialOwners (phone_number, name, volunteer_id)
VALUES (89854457280, 'Mark', null),
       (89163904237, 'Dmitriy', null),
       (89854457280, 'Mark', null),
       (89854457280, 'Mark', null);

-- add some data into table potential_owner_menu

INSERT INTO potential_owner_menu (button, call_back)
VALUES ('Добро пожаловать', 'Добро пожаловать'),
       ('Добро пожаловать', 'Добро пожаловать'),
       ('Добро пожаловать', 'Добро пожаловать');

-- -- add some data into table recommendations
--
-- INSERT INTO recommendations (rules, documents, transportation_info, arrangement_info,
--                              cynologist_advices, cynologists, common_rejects)
-- VALUES ('null', 'null', 'null', 'null', 'null', 'null', 'null'),
--        ('null', 'null', 'null', 'null', 'null', 'null', 'null');

-- add some data into table reports

INSERT INTO reports (file_path, file_size, photo, date_time, condition, diet,
                     changes, mediaType, potential_owner_id)
VALUES ('null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
       ('null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null');

-- add some data into table shelters

INSERT INTO shelters (location, description, email, how_to_find_us)
VALUES ('Astana, adress', 'We have dogs mostly', 'Astana_dog_shelter_1992@gmail.com', 'google it'),
       ('Astana, adress', 'We have cats mostly', 'Astana_cat_shelter_2003@gmail.com', 'google it');

-- add some data into table volunteers

INSERT INTO volunteers (name, phone_number, email, busy, shelter_id, potential_owner_id)
VALUES ('Agatha', 87825172840, 'Agatha_volunteer_@gmail.com', false, null, null),
       ('Mark', 89605397817, 'Mark_volunteer_@mail.ru', false, null, null),
       ('Bethony', 89255887913, 'Bethany_volunteer_@gmail.com', false, null, null),
       ('Tracy', 89166207830, 'Tracy_volunteer_@yandex.ru', false, null, null);


