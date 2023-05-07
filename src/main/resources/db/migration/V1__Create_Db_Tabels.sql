create table categories
(
    id         serial
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    name       varchar(255) not null
        constraint uk_t8o6pivur7nn124jehx7cygw5
            unique,
    show       boolean,
    parent_id  integer
        constraint fksaok720gsu4u2wrgbk10b5n8d
            references categories
);

create table role
(
    id         serial
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    active     boolean      not null,
    role       varchar(255) not null
        constraint uk_g50w4r0ru3g9uf6i6fr4kpro8
            unique
);

create table users
(
    id         serial
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    active     boolean      not null,
    email      varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    password   varchar(255) not null,
    role_id    integer      not null
        constraint fkp56c1712k691lhsyewcssf40f
            references role
);

create table account
(
    id              serial
        primary key,
    created_at      timestamp(6),
    updated_at      timestamp(6),
    account_type    varchar(255) not null,
    balance         numeric(10, 2),
    color           varchar(255) not null,
    currency        varchar(255) not null,
    name            varchar(255) not null,
    show_statistics boolean      not null,
    user_id         integer      not null
        constraint fkra7xoi9wtlcq07tmoxxe5jrh4
            references users
);

create table template
(
    id           serial
        primary key,
    created_at   timestamp(6),
    updated_at   timestamp(6),
    amount       numeric(10, 2),
    is_expense   boolean      not null,
    name         varchar(255) not null
        constraint uk_mc6r7feujo1wdd6vw5esv4jba
            unique,
    note         varchar(255),
    payment_type varchar(255) not null,
    account_id   integer      not null
        constraint fk2t3w5v1eswbrafxtc12nyl2c4
            references account,
    category_id  integer      not null
        constraint fk957crriym9eoo7qfo8xk30w91
            references categories,
    user_id      integer      not null
        constraint fkdjl177n13c7qmsoxp434nkml0
            references users
);

create table token
(
    id         serial
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    expired    boolean,
    revoked    boolean,
    token      varchar(255),
    user_id    integer
        constraint fkj8rfw4x0wjjyibfqq566j4qng
            references users
);

create table transaction
(
    id               serial
        primary key,
    created_at       timestamp(6),
    updated_at       timestamp(6),
    amount           numeric(10, 2),
    date             timestamp(6) not null,
    note             varchar(255),
    payee            varchar(255),
    payment_status   varchar(255) not null,
    payment_type     varchar(255) not null,
    transaction_type varchar(255) not null,
    account_id       integer      not null
        constraint fk6g20fcr3bhr6bihgy24rq1r1b
            references account,
    category_id      integer      not null
        constraint fkp46xgbp28n5kcq69xsw53qgnp
            references categories
);

