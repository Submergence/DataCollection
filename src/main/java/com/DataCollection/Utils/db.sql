create DATABASE dataCollection;
USE dataCollection;
CREATE TABLE system_user (
     id INT NOT NULL AUTO_INCREMENT,
     username VARCHAR(20) NOT NULL UNIQUE,
     password VARCHAR(32) NOT NULL,
     email VARCHAR(200),
     PRIMARY KEY(id)
);
INSERT INTO system_user (username, password, email) VALUES ('admin', 'admin', 'gengyfsjz@outlook.com');



CREATE TABLE records (
    id INT AUTO_INCREMENT,
    container_id VARCHAR(20) NOT NULL, --
    user_id INT NOT NULL,
    port INT NOT NULL,
    status INT NOT NULL DEFAULT 0, -- 0未执行, 1运行中, 2已删除
    vnc_port INT NOT NULL, --
    record LONGTEXT, --
    ctime TIMESTAMP NOT NULL ,
    PRIMARY KEY (id)
);
ALTER TABLE records ADD test_url VARCHAR(255) NOT NULL;

CREATE TABLE record(
    id char(20) NOT NULL,
    user_id INT NOT NULL,
    port INT NOT NULL,
    status INT NOT NULL default 0, #0运行中, 1已删除
    PRIMARY KEY(id)
)