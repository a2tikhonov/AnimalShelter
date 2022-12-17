-- liquibase formatted sql

-- changeSet: ydeev:1

-- creating table cynologists

CREATE TABLE IF NOT EXISTS cynologists
(
    id                BIGSERIAL PRIMARY KEY,
    name              TEXT        NOT NULL,
    phone_number      TEXT UNIQUE NOT NULL,
    specialties       TEXT        NOT NULL,
    years_of_practice TEXT        NOT NULL,
    shelter_id        BIGSERIAL REFERENCES shelters (id) DEFAULT NULL
);

-- creating table locations

CREATE TABLE IF NOT EXISTS locations
(
    id         BIGSERIAL PRIMARY KEY,
    file_path  TEXT UNIQUE,
    file_size  BIGINT,
    media_Type TEXT,
    data       bytea
);

-- creating table main_menu

CREATE TABLE IF NOT EXISTS main_menu
(
    id        BIGSERIAL PRIMARY KEY,
    button    TEXT UNIQUE NOT NULL,
    call_back TEXT UNIQUE NOT NULL
);

-- creating table new_user_menu

CREATE TABLE IF NOT EXISTS new_user_menu
(
    id        BIGSERIAL PRIMARY KEY,
    button    TEXT UNIQUE NOT NULL,
    call_back TEXT UNIQUE NOT NULL
);

-- creating table pets

CREATE TABLE IF NOT EXISTS pets
(
    id                 BIGSERIAL PRIMARY KEY,
    name               TEXT    NOT NULL,
    weight             INTEGER NOT NULL,
    age                INTEGER NOT NULL,
    breed              TEXT,
    species            TEXT,
    disabled           BOOLEAN                                    DEFAULT FALSE,
    shelter_id         BIGSERIAL REFERENCES shelters (id)         DEFAULT NULL,
    potential_owner_id BIGSERIAL REFERENCES potential_owners (id) DEFAULT NULL
);

-- creating table potentialOwners

CREATE TABLE IF NOT EXISTS potential_owners
(
    id               BIGSERIAL PRIMARY KEY NOT NULL,
    phone            TEXT UNIQUE           NOT NULL,
    name             TEXT                  NOT NULL,
    location_in_menu TEXT,
    report_id        BIGSERIAL DEFAULT NULL REFERENCES reports (id) DEFAULT NULL
);

-- creating table potential_owner_menu

CREATE TABLE IF NOT EXISTS potential_owner_menu
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    button    TEXT UNIQUE           NOT NULL,
    call_back TEXT UNIQUE           NOT NULL
);


-- creating table recommendations

CREATE TABLE IF NOT EXISTS recommendations
(
    id                         BIGSERIAL PRIMARY KEY NOT NULL,
    rules                      TEXT,
    documents                  TEXT,
    transportation_information TEXT,
    arrangement_information    TEXT,
    cynologist_advices         TEXT,
    list_of_cynologists        TEXT,
    common_reject_reasons      TEXT
);

-- creating table reports

CREATE TABLE IF NOT EXISTS reports
(
    id         BIGSERIAL PRIMARY KEY,
    file_path  TEXT,
    file_size  INTEGER,
    photo      BYTEA,
    date_time  TIMESTAMP WITHOUT TIME ZONE,
    condition  TEXT,
    diet       TEXT,
    changes    TEXT,
    media_type TEXT,
    potential_owner_id BIGSERIAL DEFAULT NULL REFERENCES reports (id) DEFAULT NULL
);

-- creating table shelter

CREATE TABLE IF NOT EXISTS shelters
(
    id                 BIGSERIAL PRIMARY KEY,
    location_info      TEXT        NOT NULL,
    description        TEXT        NOT NULL,
    email              TEXT UNIQUE NOT NULL,
    how_to_find_us     TEXT        NOT NULL,
    volunteer_id       BIGSERIAL REFERENCES volunteers (id)      DEFAULT NULL,
    pet_id             BIGSERIAL REFERENCES pets (id)            DEFAULT NULL,
    cynologist_id      BIGSERIAL REFERENCES cynologists (id)     DEFAULT NULL,
    location_id        BIGSERIAL REFERENCES locations (id)       DEFAULT NULL,
    recommendations_id BIGSERIAL REFERENCES recommendations (id) DEFAULT NULL
);


-- creating table volunteers

CREATE TABLE IF NOT EXISTS volunteers
(
    id                 BIGSERIAL PRIMARY KEY,
    name               TEXT        NOT NULL,
    phone              TEXT UNIQUE NOT NULL,
    email              TEXT UNIQUE NOT NULL,
    busy               BOOLEAN   DEFAULT FALSE,
    shelter_id         BIGSERIAL DEFAULT NULL REFERENCES shelters (id) DEFAULT NULL,
    potential_owner_id BIGSERIAL DEFAULT NULL REFERENCES potential_owners (id) DEFAULT NULL
);

-- CHANGES

-- changeSet: ydeev:2

-- modifying tables by adding foreign keys between those

-- cynologists

-- ALTER TABLE cynologists
--     ADD COLUMN shelter_id BIGSERIAL
--         REFERENCES shelters (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- pets

-- ALTER TABLE pets
--     ADD COLUMN shelter_id BIGSERIAL
--         REFERENCES shelters (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE pets
--     ADD COLUMN potential_owner_id BIGSERIAL
--         REFERENCES potential_owners (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- potential_owners

-- ALTER TABLE potential_owners
--     ADD COLUMN pet_id BIGSERIAL
--         REFERENCES pets (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- ALTER TABLE potential_owners
--     ADD COLUMN report_id BIGSERIAL
--         REFERENCES reports (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE potential_owners
--     ADD COLUMN volunteer_id BIGSERIAL
--         REFERENCES volunteers (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- reports

-- ALTER TABLE reports
--     ADD COLUMN potential_owner_id BIGSERIAL
--         REFERENCES potential_owners (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- shelters

-- ALTER TABLE shelters
--     ADD COLUMN volunteer_id BIGSERIAL
--         REFERENCES volunteers (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE shelters
--     ADD COLUMN pet_id BIGSERIAL
--         REFERENCES pets (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE shelters
--     ADD COLUMN cynologist_id BIGSERIAL
--         REFERENCES cynologists (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE shelters
--     ADD COLUMN location_id BIGSERIAL
--         REFERENCES locations (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE shelters
--     ADD COLUMN recommendations_id BIGSERIAL
--         REFERENCES recommendations (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- volunteers
--
-- ALTER TABLE volunteers
--     ADD COLUMN potential_owner_id BIGSERIAL
--         REFERENCES potential_owners (id)
--             DEFERRABLE INITIALLY DEFERRED;
--
-- ALTER TABLE volunteers
--     ADD COLUMN shelter_id BIGSERIAL
--         REFERENCES shelters (id)
--             DEFERRABLE INITIALLY DEFERRED;

-- changeSet: ydeev:3

-- add some data into table cynologists

INSERT INTO cynologists
    (name, phone_number, specialties, years_of_practice)
VALUES ('Baron', '89725551980', 'dog_master', 6),
       ('Blake', '89187831520', 'cat_master', 4),
       ('Amanda', '86127382460', 'dog_and_cat_master', 8),
       ('Tyrion', '88329651730', 'beginner', 1),
       ('Denice', '89856129017', 'adept', 10),
       ('Roger', '89857136715', 'novice', 3);

-- add some data into table locations

INSERT INTO locations
    (file_path, file_size, media_Type, data)
VALUES ('aaa', null, null, null),
       ('bbb', null, null, null),
       ('ccc', null, null, null),
       ('ddd', null, null, null),
       ('eee', null, null, null),
       ('ggg', null, null, null);

-- add some data into table main_menu

INSERT INTO main_menu
    (button, call_back)
VALUES ('Прислать отчет о питомце', 'Прислать отчет о питомце'),
       ('Как взять собаку из приюта', 'Как взять собаку из приюта'),
       ('Узнать информацию о приюте', 'Узнать информацию о приюте'),
       ('Позвать волонтера', 'Позвать волонтера');

-- -- add some data into new_user_menu

INSERT INTO new_user_menu (button, call_back)
VALUES ('О приюте', 'О приюте'),
       ('Записать контактные данные', 'Записать контактные данные'),
       ('Расписание и адрес', 'Расписание и адрес');

-- add some data into table pets

INSERT INTO pets
    (name, weight, age, breed, species, disabled)
VALUES ('Landish', 2.7, 1, 'persian', 'cat', false),
       ('Busya', 3.4, 3, 'foldex', 'cat', false),
       ('Khadjo', 73, 21, 'khajiit', 'cat', false),
       ('Neltarion', 32, 4, 'german shepherd', 'dog', false),
       ('Maligos', 16.2, 2, 'border collie', 'dog', false),
       ('Diktator', 15, 5, 'free breed dog', 'dog', false);

-- add some data into table potentional_owners

INSERT INTO potential_owners (phone, name, location_in_menu)
VALUES ('89854457280', 'Mark', null),
       ('89163904237', 'Dmitriy', null),
       ('89165559800', 'Lindsey', null),
       ('89852218570', 'Abignail', null),
       ('89172291590', 'John', null),
       ('89684157689', 'Mercy', null);

-- add some data into table potential_owner_menu

INSERT INTO potential_owner_menu (button, call_back)
VALUES ('Правила знакомства с животным', 'Его надо хорошо кормить'),
       ('Список необходимых документов', 'Паспорт'),
       ('Рекомендации по транспортировке', 'Должна быть переноска, которая больше питомца в 1,5 раза'),
       ('Обустройство дома для щенка', 'Спрячьте все то, что собака могла бы погрызть'),
       ('Обустройство дома для собаки',
        'Не забывайте уделять внимание, играть и следить за состоянием питомца. Большой собаке сложнее привыкнуть к смене обстановки'),
       ('Причины отказа',
        'Недостаточно большая квартира для конкретного питомца. Нет наличия свободного времени. Нет желания "работать" над взаимоотношениями с питомцем'),
       ('Обустройство',
        'Желательно подготовить по несколько (например, две) мисок с едой и водой, поставив их в разные места в квартире. Купите игрушки, чтобы питомцу было, чем себя занять. Иначе он займется всем тем, что не так лежит'),
       ('Советы кинолога', 'Советы кинолога');

-- add some data into table recommendations

INSERT INTO recommendations (id, rules, documents, transportation_information, arrangement_information,
                             cynologist_advices, list_of_cynologists, common_reject_reasons)
VALUES (1, 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
       (2, 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
       (3, 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
       (4, 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
       (5, 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
       (6, 'null', 'null', 'null', 'null', 'null', 'null', 'null');

-- add some data into table reports

INSERT INTO reports (file_path, file_size, photo, date_time, condition, diet,
                     changes, media_type)
VALUES (null, null, null, null, null, null, null, null),
       (null, null, null, null, null, null, null, null),
       (null, null, null, null, null, null, null, null),
       (null, null, null, null, null, null, null, null),
       (null, null, null, null, null, null, null, null),
       (null, null, null, null, null, null, null, null);

-- add some data into table shelters

INSERT INTO shelters (location_info, description, email, how_to_find_us)
VALUES ('Astana1, adress', 'We have dogs mostly', 'Astana_dog_shelter_1992@gmail.com', 'google it'),
       ('Astana2, adress', 'We have cats mostly', 'Astana_cat_shelter_2003@gmail.com', 'google it'),
       ('Astana3, adress', 'We have cats mostly', 'Astana_cat_shelter_2008@gmail.com', 'google it'),
       ('Astana4, adress', 'We have cats mostly', 'Astana_cat_shelter_2007@gmail.com', 'google it'),
       ('Astana5, adress', 'We have cats mostly', 'Astana_cat_shelter_2001@gmail.com', 'google it'),
       ('Astana6, adress', 'We have cats mostly', 'Astana_cat_shelter_2004@gmail.com', 'google it');

-- add some data into table volunteers

INSERT INTO volunteers (name, phone, email, busy)
VALUES ('Agatha', '87825172840', 'Agatha_volunteer_@gmail.com', false),
       ('Mark', '89605397817', 'Mark_volunteer_@mail.ru', false),
       ('Bethony', '89255887913', 'Bethany_volunteer_@gmail.com', false),
       ('Tracy', '89166207830', 'Tracy_volunteer_@yandex.ru', false),
       ('Gabby', '89267448592', 'Gabby_volunteer_@yandex.ru', false),
       ('Juel', '89571281901', 'Juel_volunteer_@yandex.ru', false);
