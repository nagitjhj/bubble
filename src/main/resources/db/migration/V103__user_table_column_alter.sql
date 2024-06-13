ALTER TABLE user ADD COLUMN email varchar(50);

ALTER TABLE user ADD COLUMN login_failed int DEFAULT 0;

ALTER TABLE user ADD COLUMN lock_yn char(1) DEFAULT 'N';

ALTER TABLE user ADD COLUMN lock_date TIMESTAMP;
