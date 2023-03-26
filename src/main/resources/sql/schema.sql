drop schema if exists public cascade;
create schema public;


create table public.adherent
(
    id           int8 not null,
    first_name   varchar(255),
    last_name    varchar(255),
    mail_address varchar(320),
    phone_number varchar(50),
    primary key (id)
);

create table public.administrator
(
    id         int8         not null,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255) not null,
    username   varchar(255) not null,
    primary key (id)
);

create table public.category
(
    id               int8        not null,
    lower_case_title varchar(50) not null,
    title            varchar(50) not null,
    primary key (id)
);

create table public.creator
(
    id                    int8        not null,
    city                  varchar(255),
    country               varchar(255),
    house_number          varchar(50),
    mail_address          varchar(320),
    phone_number          varchar(50),
    post_code             varchar(50),
    street                varchar(255),
    website               varchar(255),
    first_name            varchar(50),
    last_name             varchar(50) not null,
    lower_case_first_name varchar(50),
    lower_case_last_name  varchar(50) not null,
    role                  int4        not null,
    primary key (id)
);

create table public.game
(
    id                   int8         not null,
    description          varchar(2000),
    lower_case_title     varchar(255) not null,
    material             varchar(2000),
    max_age              int2         not null check (max_age >= 0 AND max_age <= 100),
    max_number_of_player int2         not null check (max_number_of_player >= 0 AND max_number_of_player <= 100),
    min_age              int2         not null check (min_age >= 0 AND min_age <= 100),
    min_month            int2         not null check (min_month >= 0 AND min_month <= 100),
    min_number_of_player int2         not null check (min_number_of_player >= 1 AND min_number_of_player <= 100),
    nature               int4,
    play_time            varchar(255),
    rules                oid,
    title                varchar(255) not null,
    variant              oid,
    year_of_release      date,
    primary key (id)
);

create table public.game_category
(
    fk_game     int8 not null,
    fk_category int8 not null,
    primary key (fk_game, fk_category)
);

create table public.game_copy
(
    id                    int8         not null,
    date_of_purchase      date,
    date_of_registration  date         not null,
    edition_number        varchar(255),
    general_state         int4         not null,
    is_available_for_loan boolean,
    location              varchar(255),
    object_code           varchar(255) not null,
    price                 numeric(12, 2),
    wear_condition        varchar(255) not null,
    fk_game               int8         not null,
    fk_publisher          int8,
    primary key (id)
);

create table public.game_copy_pre_reservation
(
    fk_pre_reservation int8 not null,
    fk_game_copy       int8 not null,
    primary key (fk_pre_reservation, fk_game_copy)
);

create table public.game_copy_reservation
(
    fk_reservation int8    not null,
    is_late        boolean not null,
    fk_game_copy   int8    not null,
    primary key (fk_reservation)
);

create table public.game_creator
(
    fk_game    int8 not null,
    fk_creator int8 not null,
    primary key (fk_game, fk_creator)
);

create table public.game_mechanism
(
    fk_game      int8 not null,
    fk_mechanism int8 not null,
    primary key (fk_game, fk_mechanism)
);

create table public.image
(
    id      int8 not null,
    fk_game int8,
    primary key (id)
);

create table public.image_blob
(
    fk_image int8  not null,
    content  bytea not null,
    primary key (fk_image)
);

create table public.mechanism
(
    id               int8        not null,
    lower_case_title varchar(50) not null,
    title            varchar(50) not null,
    primary key (id)
);

create table public.pre_reservation
(
    id                   int8    not null,
    code                 int4,
    date_of_ending       date,
    date_of_start        date,
    date_time_of_closure timestamp,
    date_time_of_demand  timestamp,
    is_closed            boolean not null,
    primary key (id)
);

create table public.publisher
(
    id              int8         not null,
    city            varchar(255),
    country         varchar(255),
    house_number    varchar(50),
    mail_address    varchar(320),
    phone_number    varchar(50),
    post_code       varchar(50),
    street          varchar(255),
    website         varchar(255),
    lower_case_name varchar(255) not null,
    name            varchar(255) not null,
    primary key (id)
);

create table public.reservation
(
    id                      int8 not null,
    date_of_ending          date,
    date_of_start           date not null,
    date_time_of_creation   timestamp,
    date_time_of_return     timestamp,
    date_time_of_withdrawal timestamp,
    status                  int4 not null,
    total_amount            numeric(12, 2),
    fk_adherent             int8,
    fk_administrator        int8,
    primary key (id)
);

alter table public.category
    add constraint unique_cat unique (lower_case_title);

alter table public.creator
    add constraint UKbt1nbhhd0wpddqp6xban3ht8s unique (lower_case_first_name, lower_case_last_name);

alter table public.game
    add constraint UKa2fnc5ur18i47ltl329kbx6ya unique (lower_case_title);

alter table public.game_copy
    add constraint UK9l4nvuj260e8j0rcli5n9lkf2 unique (object_code);

alter table public.mechanism
    add constraint UK1axt2rwrabs9llw9d8em6xo6 unique (lower_case_title);

alter table public.publisher
    add constraint UKjn8ea0tdummnwy0crrueojpuv unique (lower_case_name);
create sequence adherent_sequence start 1 increment 50;
create sequence administrator_sequence start 1 increment 50;
create sequence category_sequence start 1 increment 50;
create sequence creator_sequence start 1 increment 50;
create sequence game_copy_sequence start 1 increment 50;
create sequence game_sequence start 1 increment 50;
create sequence image_sequence start 1 increment 50;
create sequence mechanism_sequence start 1 increment 50;
create sequence pre_reservation_sequence start 1 increment 50;
create sequence publisher_sequence start 1 increment 50;
create sequence reservation_sequence start 1 increment 50;

alter table public.game_category
    add constraint FKq5aaq1l336eanl9fsh0b76nmj
        foreign key (fk_category)
            references public.category;

alter table public.game_category
    add constraint FK84qsxm812h9nqdu9kl7gevl0t
        foreign key (fk_game)
            references public.game;

alter table public.game_copy
    add constraint FKotj002ttbfm6dhl5up8anrfno
        foreign key (fk_game)
            references public.game;

alter table public.game_copy
    add constraint FKjeaoxlwmb5391tdhcuv2oiusy
        foreign key (fk_publisher)
            references public.publisher;

alter table public.game_copy_pre_reservation
    add constraint FKlkcjrwb621pcv2nmndm4euke3
        foreign key (fk_game_copy)
            references public.game_copy;

alter table public.game_copy_pre_reservation
    add constraint FK3c0a82ygqqhetnb6239sg61t4
        foreign key (fk_pre_reservation)
            references public.pre_reservation;

alter table public.game_copy_reservation
    add constraint FKryk7ex3iwikhgx9ieaum4s0jh
        foreign key (fk_game_copy)
            references public.game_copy;

alter table public.game_copy_reservation
    add constraint FKaf9qqk1wb5nnhc3srrrnsdjf1
        foreign key (fk_reservation)
            references public.reservation;

alter table public.game_creator
    add constraint FKshotpioqxxaqreebwecwwmnlt
        foreign key (fk_creator)
            references public.creator;

alter table public.game_creator
    add constraint FKerab5eayuhno1x87x3x8gq8x
        foreign key (fk_game)
            references public.game;

alter table public.game_mechanism
    add constraint FKnpd7wuawxayh5raer6n75mefx
        foreign key (fk_mechanism)
            references public.mechanism;

alter table public.game_mechanism
    add constraint FKbyrbldhddommq7238ex18c6ri
        foreign key (fk_game)
            references public.game;

alter table public.image
    add constraint FK52ykfhxot45jfxiuky8ccqhvy
        foreign key (fk_game)
            references public.game;

alter table public.image_blob
    add constraint FKepjwu9m1yuf3p29gexxh59f2o
        foreign key (fk_image)
            references public.image;

alter table public.reservation
    add constraint FK67jy47w04wfv0ftl8kams6y8y
        foreign key (fk_adherent)
            references public.adherent;

alter table public.reservation
    add constraint FKdrm0nye3pvouds2v4xutw01mp
        foreign key (fk_administrator)
            references public.administrator;
