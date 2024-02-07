create table USERS
(
    ID           NUMBER(19)    not null
        primary key,
    CREATED      DATE,
    UPDATED      DATE,
    BIRTH_DATE   DATE,
    EMAIL        VARCHAR2(255) not null,
    FIRST_NAME   VARCHAR2(255) not null,
    FULL_NAME    VARCHAR2(255) not null,
    GENDER       VARCHAR2(255),
    LAST_NAME    VARCHAR2(255) not null,
    LOGIN        VARCHAR2(255),
    NOTES        VARCHAR2(200),
    PASSWORD     VARCHAR2(10),
    PHONE_NUMBER VARCHAR2(255),
    PICTURE      VARCHAR2(255),
    SYSTEM_ID    VARCHAR2(255)
);

ALTER TABLE USERS ADD (
    constraint users_PK PRIMARY KEY (ID),
    constraint users_EMAIL
        unique (EMAIL),
    constraint users_LOGIN
        unique (LOGIN),
    constraint users_FULL_NAME
        unique (FULL_NAME)
);
CREATE SEQUENCE USERS_SEQUENCE;

