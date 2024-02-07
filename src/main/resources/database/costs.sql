create table COSTS
(
    ID      NUMBER(19)    not null
        primary key,
    CREATED DATE,
    UPDATED DATE,
    NAME    VARCHAR2(255) not null
);