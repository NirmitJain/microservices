CREATE TABLE IF NOT EXISTS cards(
 card_id bigint NOT NULL AUTO_INCREMENT,
 mobile_number varchar(10) NOT NULL,
 card_number varchar(16) NOT NULL,
 card_type varchar(20) NOT NULL,
 total_limits int NOT NULL,
 amount_used int NOT NULL,
 available_amount int NOT NULL,
 created_at date NOT NULL,
 created_by varchar(25) NOT NULL,
 updated_by varchar(25) DEFAULT NULL,
 updated_at date DEFAULT NULL,
 PRIMARY KEY(card_id)
);