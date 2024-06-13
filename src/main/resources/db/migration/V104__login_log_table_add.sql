CREATE TABLE login_log
(
    seq bigint auto_increment primary key,
    id varchar(50),
    login_date timestamp DEFAULT now()
);

ALTER TABLE user ADD COLUMN provider varchar(100);

ALTER TABLE user ADD COLUMN providerId varchar(100);
