CREATE TABLE subscribe
(
    seq int AUTO_INCREMENT PRIMARY KEY,
    user_id varchar(100),
    pub_id varchar(100),
    start_date timestamp,
    end_date timestamp,
    valid_yn char(1),
    times int
);

CREATE TABLE message
(
    seq int AUTO_INCREMENT PRIMARY KEY,
    writer_id varchar(100),
    pub_id varchar(100),
    content varchar(250),
    send_date timestamp DEFAULT now()
);

CREATE TABLE entry
(
    seq int AUTO_INCREMENT PRIMARY KEY,
    user_id varchar(100),
    session_id varchar(200),
    pub_id varchar(100),
    in_date timestamp,
    out_date timestamp,
    in_yn char(1)
);
