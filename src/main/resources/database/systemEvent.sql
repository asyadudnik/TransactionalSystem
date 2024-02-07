create table SYSTEM_EVENTS
(
    ID              NUMBER(19)    not null
        primary key,
    CREATED         DATE,
    UPDATED         DATE,
    DESCRIPTION     VARCHAR2(255),
    LOCK_END_TIME   DATE,
    LOCK_START_TIME DATE,
    NAME            VARCHAR2(255) not null,
    PRIORITY        NUMBER(10)    not null,
    STATUS          VARCHAR2(255) not null
)
