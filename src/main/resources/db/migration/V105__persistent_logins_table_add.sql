CREATE TABLE persistent_login
(
    username varchar(100) not null,
    series varchar(100) primary key,
    token varchar(100) not null,
    last_used timestamp not null
);