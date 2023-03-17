drop table if exists public.administrator cascade;

drop table if exists public.image_blob cascade;

drop table if exists public.image cascade;

drop table if exists public.loan cascade;

drop table if exists public.account cascade;

drop table if exists public.loan_status cascade;

drop table if exists public.reservation cascade;

drop table if exists public.game_category cascade;

drop table if exists public.category cascade;

drop table if exists public.game_copy_pre_reservation cascade;

drop table if exists public.game_copy cascade;

drop table if exists public.pre_reservation cascade;

drop table if exists public.publisher cascade;

drop table if exists public.game_creator cascade;

drop table if exists public.creator cascade;

drop table if exists public.game_mechanism cascade;

drop table if exists public.game cascade;

drop table if exists public.mechanism cascade;



drop sequence if exists public.account_sequence;

drop sequence if exists public.category_sequence;

drop sequence if exists public.creator_sequence;

drop sequence if exists public.game_copy_sequence;

drop sequence if exists public.game_sequence;

drop sequence if exists public.image_sequence;

drop sequence if exists public.loan_sequence;

drop sequence if exists public.loan_status_sequence;

drop sequence if exists public.mechanism_sequence;

drop sequence if exists public.pre_reservation_sequence;

drop sequence if exists public.publisher_sequence;



create table public.account
(
    id                int8         not null,
    city              varchar(255),
    country           varchar(255),
    house_number      varchar(50),
    mail_address      varchar(320),
    phone_number      varchar(50),
    post_code         varchar(50),
    street            varchar(255),
    website           varchar(255),
    first_name        varchar(127),
    last_name         varchar(127),
    membership_number varchar(50)  not null,
    renewal_date      date,
    username          varchar(255) not null,
    primary key (id)
);

create table public.administrator
(
    id int8 not null,
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

create table public.image
(
    id      int8 not null,
    fk_game int8,
    primary key (id)
);

create table public.image_blob
(
    content  bytea not null,
    image_id int8  not null,
    primary key (image_id)
);

create table public.loan
(
    id              int8    not null,
    is_closed       boolean not null,
    loan_end_time   date    not null,
    loan_start_time date    not null,
    fk_account      int8,
    fk_game_copy    int8,
    primary key (id)
);

create table public.loan_status
(
    id             int8         not null,
    description    varchar(255) not null,
    lower_case_tag varchar(50)  not null,
    tag            varchar(50)  not null,
    primary key (id)
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
    id                   int8 not null,
    date_of_ending       date,
    date_of_start        date,
    scheduled_return     timestamp,
    scheduled_withdrawal timestamp,
    primary key (id)
);

alter table public.account
    add constraint UKgex1lmaqpg0ir5g1f5eftyaa1 unique (username);

alter table public.category
    add constraint unique_cat unique (lower_case_title);

alter table public.creator
    add constraint UKbt1nbhhd0wpddqp6xban3ht8s unique (lower_case_first_name, lower_case_last_name);

alter table public.game
    add constraint UKa2fnc5ur18i47ltl329kbx6ya unique (lower_case_title);

alter table public.game_copy
    add constraint UK9l4nvuj260e8j0rcli5n9lkf2 unique (object_code);

alter table public.loan_status
    add constraint UKonr6nwvhdyjwbluwg67khjywc unique (lower_case_tag);

alter table public.mechanism
    add constraint UK1axt2rwrabs9llw9d8em6xo6 unique (lower_case_title);

alter table public.publisher
    add constraint UKjn8ea0tdummnwy0crrueojpuv unique (lower_case_name);
create sequence account_sequence start 1 increment 50;
create sequence category_sequence start 1 increment 50;
create sequence creator_sequence start 1 increment 50;
create sequence game_copy_sequence start 1 increment 50;
create sequence game_sequence start 1 increment 50;
create sequence image_sequence start 1 increment 50;
create sequence loan_sequence start 1 increment 50;
create sequence loan_status_sequence start 1 increment 50;
create sequence mechanism_sequence start 1 increment 50;
create sequence pre_reservation_sequence start 1 increment 50;
create sequence publisher_sequence start 1 increment 50;

create table game_category
(
    fk_game     int8 not null,
    fk_category int8 not null,
    primary key (fk_game, fk_category)
);

create table game_copy_pre_reservation
(
    fk_pre_reservation int8 not null,
    fk_game_copy       int8 not null,
    primary key (fk_pre_reservation, fk_game_copy)
);

create table game_creator
(
    fk_game    int8 not null,
    fk_creator int8 not null,
    primary key (fk_game, fk_creator)
);

create table game_mechanism
(
    fk_game      int8 not null,
    fk_mechanism int8 not null,
    primary key (fk_game, fk_mechanism)
);

alter table public.game_copy
    add constraint FKotj002ttbfm6dhl5up8anrfno
        foreign key (fk_game)
            references public.game;

alter table public.game_copy
    add constraint FKjeaoxlwmb5391tdhcuv2oiusy
        foreign key (fk_publisher)
            references public.publisher;

alter table public.image
    add constraint FK52ykfhxot45jfxiuky8ccqhvy
        foreign key (fk_game)
            references public.game;

alter table public.image_blob
    add constraint FKwh9ounh5rvc9m2wn4ssawvyc
        foreign key (image_id)
            references public.image;

alter table public.loan
    add constraint FKld6heuq0u88f3k9yrg4r64sk0
        foreign key (fk_account)
            references public.account;

alter table public.loan
    add constraint FK75c47nffje4co69co4jekkcgc
        foreign key (fk_game_copy)
            references public.game_copy;

alter table game_category
    add constraint FKq5aaq1l336eanl9fsh0b76nmj
        foreign key (fk_category)
            references public.category;

alter table game_category
    add constraint FK84qsxm812h9nqdu9kl7gevl0t
        foreign key (fk_game)
            references public.game;

alter table game_copy_pre_reservation
    add constraint FKlkcjrwb621pcv2nmndm4euke3
        foreign key (fk_game_copy)
            references public.game_copy;

alter table game_copy_pre_reservation
    add constraint FK3c0a82ygqqhetnb6239sg61t4
        foreign key (fk_pre_reservation)
            references public.pre_reservation;

alter table game_creator
    add constraint FKshotpioqxxaqreebwecwwmnlt
        foreign key (fk_creator)
            references public.creator;

alter table game_creator
    add constraint FKerab5eayuhno1x87x3x8gq8x
        foreign key (fk_game)
            references public.game;

alter table game_mechanism
    add constraint FKnpd7wuawxayh5raer6n75mefx
        foreign key (fk_mechanism)
            references public.mechanism;

alter table game_mechanism
    add constraint FKbyrbldhddommq7238ex18c6ri
        foreign key (fk_game)
            references public.game;
