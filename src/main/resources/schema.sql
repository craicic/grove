DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE SEQUENCE account_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE category_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE creator_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE game_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE game_copy_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE image_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE loan_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE loan_status_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE product_line_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE publisher_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE seller_sequence START 100 INCREMENT BY 50;
CREATE SEQUENCE theme_sequence START 100 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS category
(
    id              BIGINT      NOT NULL DEFAULT nextval('category_sequence'),
    lower_case_name VARCHAR(50) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    CONSTRAINT category_pkey
        PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uniq_cat ON category (lower_case_name);
ALTER TABLE category
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS account
(
    id                BIGINT       NOT NULL DEFAULT nextval('account_sequence'),
    first_name        VARCHAR(127),
    last_name         VARCHAR(127),
    membership_number VARCHAR(50)  NOT NULL,
    renewal_date      DATE,
    username          VARCHAR(255) NOT NULL,
    city              VARCHAR(255),
    country           VARCHAR(255) NOT NULL,
    mail_address      VARCHAR(320),
    phone_number      VARCHAR(50),
    post_code         VARCHAR(50),
    street            VARCHAR(255),
    house_number      VARCHAR(50),
    website           VARCHAR(255),
    CONSTRAINT account_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_acc ON account (username);
ALTER TABLE account
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS creator
(
    id                    BIGINT       NOT NULL DEFAULT nextval('creator_sequence'),
    first_name            VARCHAR(50),
    last_name             VARCHAR(50)  NOT NULL,
    lower_case_first_name VARCHAR(50)  NOT NULL,
    lower_case_last_name  VARCHAR(50)  NOT NULL,
    role                  INTEGER      NOT NULL,
    city                  VARCHAR(255),
    country               VARCHAR(255) NOT NULL,
    mail_address          VARCHAR(320),
    phone_number          VARCHAR(50),
    post_code             VARCHAR(50),
    street                VARCHAR(255),
    house_number          VARCHAR(50),
    website               VARCHAR(255),
    CONSTRAINT creator_pkey
        PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS unique_name ON creator (lower_case_first_name, lower_case_last_name);
ALTER TABLE creator
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS loan_status
(
    id             BIGINT       NOT NULL DEFAULT nextval('loan_sequence'),
    description    VARCHAR(255) NOT NULL,
    lower_case_tag VARCHAR(50)  NOT NULL,
    tag            VARCHAR(50)  NOT NULL,
    CONSTRAINT loan_status_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS lower_case_tag ON loan_status (lower_case_tag);
ALTER TABLE loan_status
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS product_line
(
    id              BIGINT       NOT NULL DEFAULT nextval('product_line_sequence'),
    lower_case_name VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    CONSTRAINT product_line_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_pro ON product_line (lower_case_name);
ALTER TABLE product_line
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game
(
    id                   BIGINT       NOT NULL DEFAULT nextval('game_sequence'),
    core_rules           oid,
    description          VARCHAR(1000),
    edition_number       VARCHAR(255),
    ending               oid,
    goal                 VARCHAR(1000),
    lower_case_name      VARCHAR(255) NOT NULL,
    max_age              SMALLINT     NOT NULL,
    max_number_of_player SMALLINT     NOT NULL,
    min_age              SMALLINT     NOT NULL,
    min_month            SMALLINT     NOT NULL,
    min_number_of_player SMALLINT     NOT NULL,
    name                 VARCHAR(255) NOT NULL,
    nature               INTEGER,
    play_time            VARCHAR(20),
    preparation          oid,
    size                 VARCHAR(255),
    stuff                VARCHAR(1000),
    variant              oid,
    core_game_id         BIGINT,
    fk_product_line      BIGINT,
    CONSTRAINT game_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (core_game_id) REFERENCES game,
    FOREIGN KEY (fk_product_line) REFERENCES product_line
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_gam ON game (lower_case_name);
ALTER TABLE game
    OWNER TO postgres;

ALTER TABLE game
    ADD CONSTRAINT game_max_age_check
        CHECK ((max_age >= 0) AND (max_age <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_max_number_of_player_check
        CHECK ((max_number_of_player >= 0) AND (max_number_of_player <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_min_age_check
        CHECK ((min_age >= 0) AND (min_age <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_min_month_check
        CHECK ((min_month >= 0) AND (min_month <= 100));

ALTER TABLE game
    ADD CONSTRAINT game_min_number_of_player_check
        CHECK ((min_number_of_player >= 1) AND (min_number_of_player <= 100));

CREATE TABLE IF NOT EXISTS image
(
    id      BIGINT NOT NULL DEFAULT nextval('image_sequence'),
    fk_game BIGINT,
    CONSTRAINT image_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_game) REFERENCES game
);

ALTER TABLE image
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS image_blob
(
    image_id BIGINT NOT NULL,
    content  oid    NOT NULL,
    FOREIGN KEY (image_id) REFERENCES image
);

ALTER TABLE image_blob
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS publisher
(
    id              BIGINT       NOT NULL DEFAULT nextval('publisher_sequence'),
    lower_case_name VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    city            VARCHAR(255),
    country         VARCHAR(255) NOT NULL,
    mail_address    VARCHAR(320),
    phone_number    VARCHAR(50),
    post_code       VARCHAR(50),
    street          VARCHAR(255),
    house_number    VARCHAR(50),
    website         VARCHAR(255),
    CONSTRAINT publisher_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_pub ON publisher (lower_case_name);
ALTER TABLE publisher
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS seller
(
    id              BIGINT       NOT NULL DEFAULT nextval('seller_sequence'),
    lower_case_name VARCHAR(255) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    city            VARCHAR(255),
    country         VARCHAR(255) NOT NULL,
    mail_address    VARCHAR(320),
    phone_number    VARCHAR(50),
    post_code       VARCHAR(50),
    street          VARCHAR(255),
    house_number    VARCHAR(50),
    website         VARCHAR(255),
    CONSTRAINT seller_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_sel ON seller (lower_case_name);
ALTER TABLE seller
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_copy
(
    id               BIGINT       NOT NULL DEFAULT nextval('game_copy_sequence'),
    date_of_purchase DATE,
    general_state    INTEGER      NOT NULL,
    is_loanable      BOOLEAN,
    location         VARCHAR(255),
    object_code      VARCHAR(255) NOT NULL,
    price            NUMERIC(12, 2),
    register_date    date         NOT NULL,
    wear_condition   VARCHAR(255) NOT NULL,
    fk_game          BIGINT       NOT NULL,
    fk_publisher     BIGINT,
    fk_seller        BIGINT,
    CONSTRAINT game_copy_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_game) REFERENCES game,
    FOREIGN KEY (fk_publisher) REFERENCES publisher,
    FOREIGN KEY (fk_seller) REFERENCES seller
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_obj ON game_copy (object_code);
ALTER TABLE game_copy
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS loan
(
    id              BIGINT  NOT NULL DEFAULT nextval('loan_sequence'),
    is_closed       BOOLEAN NOT NULL,
    loan_end_time   date    NOT NULL,
    loan_start_time date    NOT NULL,
    fk_account      BIGINT,
    fk_game_copy    BIGINT,
    CONSTRAINT loan_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (fk_account) REFERENCES account,
    FOREIGN KEY (fk_game_copy) REFERENCES game_copy
);
ALTER TABLE loan
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS theme
(
    id              BIGINT      NOT NULL DEFAULT nextval('theme_sequence'),
    lower_case_name VARCHAR(50) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    CONSTRAINT theme_pkey
        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS uniq_the ON theme (lower_case_name);
ALTER TABLE theme
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_category
(
    fk_game     BIGINT NOT NULL,
    fk_category BIGINT NOT NULL,
    CONSTRAINT game_category_pkey
        PRIMARY KEY (fk_game, fk_category),
    FOREIGN KEY (fk_category) REFERENCES category,
    FOREIGN KEY (fk_game) REFERENCES game
);
ALTER TABLE game_category
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_creator
(
    fk_game    BIGINT NOT NULL,
    fk_creator BIGINT NOT NULL,
    CONSTRAINT game_creator_pkey
        PRIMARY KEY (fk_game, fk_creator),
    FOREIGN KEY (fk_creator) REFERENCES creator,
    FOREIGN KEY (fk_game) REFERENCES game
);
ALTER TABLE game_creator
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS game_theme
(
    fk_game  BIGINT NOT NULL,
    fk_theme BIGINT NOT NULL,
    CONSTRAINT game_theme_pkey
        PRIMARY KEY (fk_game, fk_theme),
    FOREIGN KEY (fk_theme) REFERENCES theme,
    FOREIGN KEY (fk_game) REFERENCES game
);
ALTER TABLE game_theme
    OWNER TO postgres;


