-- MySQL Script generated by MySQL Workbench
-- 08/27/15 21:43:42
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dragonbusiness
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `dragonbusiness` ;
CREATE SCHEMA IF NOT EXISTS `dragonbusiness` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `dragonbusiness` ;


-- -----------------------------------------------------
-- Table `dragonbusiness`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dragonbusiness`.`company` ;

CREATE TABLE IF NOT EXISTS `dragonbusiness`.`company` (
  `company_id` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(60) NOT NULL,
  `company_value` MEDIUMTEXT NULL,
  `company_info` VARCHAR(600) NULL DEFAULT 'Remember to set company info!',
  `company_bankrupt` VARCHAR(45) NOT NULL DEFAULT 'false',
  `company_hiring` VARCHAR(45) NOT NULL DEFAULT 'true',
  PRIMARY KEY (`company_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dragonbusiness`.`player`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dragonbusiness`.`player` ;

CREATE TABLE IF NOT EXISTS `dragonbusiness`.`player` (
  `player_id` INT NOT NULL AUTO_INCREMENT,
  `player_uuid` VARCHAR(80) NOT NULL,
  `player_name` VARCHAR(60) NOT NULL,
  `player_rank` VARCHAR(45) NOT NULL DEFAULT 'none',
  `player_level` INT NOT NULL DEFAULT 0,
  `player_earned` MEDIUMTEXT NOT NULL,
  `player_pendingInvite` VARCHAR(200) NOT NULL DEFAULT 'none',
  `player_application` VARCHAR(200) NOT NULL DEFAULT 'none',
  `company_company_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`player_id`),
  INDEX `fk_player_company_idx` (`company_company_id` ASC),
  CONSTRAINT `fk_player_company`
    FOREIGN KEY (`company_company_id`)
    REFERENCES `dragonbusiness`.`company` (`company_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
