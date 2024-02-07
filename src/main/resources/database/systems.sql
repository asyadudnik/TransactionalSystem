create table SYSTEMS
(
    ID                   NUMBER(19)    not null
        primary key,
    CREATED              DATE,
    UPDATED              DATE,
    DB_CONNECTION_STRING VARCHAR2(255) not null,
    SYSTEM_DESCRIPTION   VARCHAR2(255),
    SYSTEM_NAME          VARCHAR2(255) not null
)ж