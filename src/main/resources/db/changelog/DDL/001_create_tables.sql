--liquibase formatted sql
--changeset techgeeknext:create-tables

CREATE TABLE users
(
    id          bigint PRIMARY KEY,
    nick_name   varchar(255) NOT NULL UNIQUE,
    password    varchar(255) NOT NULL,
    sex         bool         NOT NULL,
    fio         varchar(255),
    created     date,
    birthday    date,
    is_disabled bool         NOT NULL DEFAULT false
);
CREATE SEQUENCE user_seq START 2;
insert into users (id, fio, nick_name, password, sex)
values (1, 'admin', 'admin', '$2a$10$ijT1sp7BekiHaqFTFMntxuzLigUBAabM9WWy/jIvoZsqQRy8ms.ae', true);

CREATE TABLE role
(
    id    bigint PRIMARY KEY,
    code  varchar(100) NOT NULL,
    descr varchar(255) NOT NULL
);
insert into role (id, code, descr)
values (1, 'CLIENT', 'Роль клиента');
insert into role (id, code, descr)
values (2, 'MANAGER', 'Роль менеджера');
insert into role (id, code, descr)
values (3, 'OWNER', 'Роль владельца');
insert into role (id, code, descr)
values (4, 'ADMIN', 'Роль админа');

CREATE TABLE coordinate
(
    id           bigint PRIMARY KEY,
    longitude    varchar(255) NOT NULL,
    latitude     varchar(255) NOT NULL,
    full_address varchar(1000),
    fias_code    varchar(255)
);
CREATE SEQUENCE coordinate_seq START 1;

CREATE TABLE wallet
(
    id bigint PRIMARY KEY
);
CREATE SEQUENCE wallet_seq START 1;

CREATE TABLE metadata
(
    id          bigint PRIMARY KEY,
    description varchar(2000)
);
CREATE SEQUENCE metadata_seq START 1;

CREATE TABLE payment_info
(
    id           bigint PRIMARY KEY,
    is_payment   boolean NOT NULL DEFAULT false,
    payment_date TIMESTAMP
);
CREATE SEQUENCE payment_info_seq START 1;

CREATE TABLE unit
(
    id            bigint PRIMARY KEY,
    name          varchar(1000) NOT NULL,
    owner_unit    varchar(1000) NOT NULL,
    ogrn          varchar(1000) NOT NULL,
    coordinate_id bigint        NOT NULL REFERENCES coordinate (id),
    owner_id      bigint        NOT NULL REFERENCES users (id),
    wallet_id     bigint REFERENCES wallet (id),
    meta_id       bigint REFERENCES metadata (id)
);
CREATE SEQUENCE unit_seq START 1;

CREATE TABLE user_role
(
    id      bigint PRIMARY KEY,
    user_id bigint NOT NULL REFERENCES users (id),
    role_id bigint NOT NULL REFERENCES role (id),
    unit_id bigint REFERENCES unit (id)
);
CREATE SEQUENCE user_role_seq START 2;
insert into user_role (id, user_id, role_id, unit_id)
values (1, 1, 4, NULL);

CREATE TABLE rang
(
    id      bigint PRIMARY KEY,
    value   int NOT NULL DEFAULT 0,
    unit_id bigint REFERENCES unit (id),
    CHECK (value BETWEEN 0 AND 5)
);
CREATE SEQUENCE rang_seq START 1;
CREATE INDEX rang_unit ON rang (unit_id);

CREATE TABLE unit_table
(
    id          bigint PRIMARY KEY,
    description varchar(255),
    count_place integer NOT NULL DEFAULT 1,
    unit_id     bigint REFERENCES unit (id)
);
CREATE SEQUENCE unit_table_seq START 1;

CREATE TABLE menu
(
    id          bigint PRIMARY KEY,
    name        varchar(255) NOT NULL,
    description varchar(2000),
    unit_id     bigint REFERENCES unit (id)
);
CREATE SEQUENCE menu_seq START 1;

CREATE TABLE dish
(
    id          bigint PRIMARY KEY,
    name        varchar(255) NOT NULL,
    description varchar(2000),
    category    varchar(255),
    cost        float,
    menu_id     bigint REFERENCES menu (id)
);
CREATE SEQUENCE dish_seq START 1;

CREATE TABLE order_client
(
    id         bigint PRIMARY KEY,
    is_done    boolean     NOT NULL DEFAULT false,
    status     varchar(20) NOT NULL,
    to_date    TIMESTAMP,
    unit_id    bigint REFERENCES unit (id),
    client_id  bigint REFERENCES users (id),
    payment_id bigint REFERENCES payment_info (id),
    table_id   bigint REFERENCES unit_table (id)
);
CREATE SEQUENCE order_client_seq START 1;

CREATE TABLE order_client_dish
(
    id       bigint PRIMARY KEY,
    count    integer NOT NULL DEFAULT 1,
    order_id bigint REFERENCES order_client (id),
    dish_id  bigint REFERENCES dish (id)
);
CREATE SEQUENCE order_basket_seq START 1;

CREATE TABLE unit_news
(
    id          bigint PRIMARY KEY,
    description varchar(4000) NOT NULL,
    unit_id     bigint REFERENCES unit (id)
);
CREATE SEQUENCE unit_news_seq START 1;

CREATE TABLE bucket
(
    id        bigint PRIMARY KEY,
    count     integer NOT NULL DEFAULT 1,
    client_id bigint REFERENCES users (id),
    dish_id   bigint REFERENCES dish (id)
);
CREATE SEQUENCE bucket_seq START 1;

CREATE TABLE comment
(
    id      bigint PRIMARY KEY,
    message varchar(2000) NOT NULL,
    value   int,
    unit_id bigint REFERENCES unit (id),
    CHECK (value BETWEEN 0 AND 5)
);
CREATE SEQUENCE comment_seq START 1;

CREATE TABLE file_meta
(
    id           bigint PRIMARY KEY,
    metadata_id  bigint REFERENCES metadata (id),
    unit_id      bigint REFERENCES unit (id),
    unit_news_id bigint REFERENCES unit_news (id),
    dish_id      bigint REFERENCES dish (id),
    file_path    varchar(300) NOT NULL UNIQUE
);
CREATE SEQUENCE file_meta_seq START 1;