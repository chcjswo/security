CREATE TABLE IF NOT EXISTS `security`.`users` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` VARCHAR(45) NOT NULL,
	`password` TEXT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `security`.`otp` (
  	`id` INT NOT NULL AUTO_INCREMENT,
  	`user_id` VARCHAR(45) NOT NULL,
	`otp_code` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`id`)
);