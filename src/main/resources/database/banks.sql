create table banks
(
    id      number(19, 0) not null,
    created date,
    updated date,
    bic     varchar2(255) not null,
    iban    varchar2(255) not null,
    name    varchar2(255) not null,
    primary key (id)
);