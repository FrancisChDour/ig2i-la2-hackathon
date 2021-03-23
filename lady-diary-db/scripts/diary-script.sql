-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hackathon
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hackathon
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hackathon` ;
GRANT ALL PRIVILEGES ON `hackathon`.* TO 'ladydiary'@'%' IDENTIFIED BY 'PasLyreco123';
USE `hackathon` ;

-- -----------------------------------------------------
-- Table `hackathon`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hackathon`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NULL,
  `token` CHAR(37) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hackathon`.`topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hackathon`.`topic` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `id_owner` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_topic_1_idx` (`id_owner` ASC) VISIBLE,
  CONSTRAINT `fk_topic_1`
    FOREIGN KEY (`id_owner`)
    REFERENCES `hackathon`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hackathon`.`record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hackathon`.`record` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` DATETIME NOT NULL,
  `record_date` DATETIME NULL,
  `id_topic` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_record_1_idx` (`id_topic` ASC) VISIBLE,
  CONSTRAINT `fk_record_1`
    FOREIGN KEY (`id_topic`)
    REFERENCES `hackathon`.`topic` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hackathon`.`data_record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hackathon`.`data_record` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(255) NOT NULL,
  `value` TEXT NOT NULL,
  `id_record` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_data_record_1_idx` (`id_record` ASC) VISIBLE,
  CONSTRAINT `fk_data_record_1`
    FOREIGN KEY (`id_record`)
    REFERENCES `hackathon`.`record` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- INSERTS
INSERT INTO `hackathon`.`user`
	(`name`,`password`)
VALUES
('jacky','toto'),('virgile','titi'),('moi','salut');

INSERT INTO `hackathon`.`topic`
	(`creation_date`,`name`,`id_owner`)
VALUES
	('2021-03-15T00:00:00', 'Hackathon',1),
	('2021-03-18T00:00:00', 'EcoloBobo',1),
	('2021-03-12T00:00:00', 'Ecole', 3);

INSERT INTO `hackathon`.`record`
	(`creation_date`,`record_date`,`id_topic`)
VALUES
	('2021-03-16T01:00:00',NULL,1),
    ('2021-03-16T09:00:00','2021-03-15T23:00:58',3),
    ('2021-03-17T08:00:00','2021-03-16T23:22:58',3),
    ('2021-03-17T23:00:00',NULL,3);

INSERT INTO `hackathon`.`data_record`
	(`key`,`value`,`id_record`)
VALUES
	('Commentaire', 'Aujourdh\'ui, Sacha a été très gentil avec moi', 1),
	('Son insta', '@sacha_lsr', 1),
    ('Commentaire','Aujourdhui j\'ai cassé le projet du hackathon. Martin était un peu furieux contre moi.',2),
    ('Repas midi','Pates bolo',2),
    ('Commentaire','Aujourdhui j\'ai recassé le projet du hackathon. Victorine n\était pas très contente.',3),
    ('Repas midi','Pates carbo', 3),
    ('Commentaire','Aujourdhui j\'ai construit douze usines. Guillaume et Francis étaient contents.',4),
    ('Repas midi','Pates à l\'eau', 4);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

