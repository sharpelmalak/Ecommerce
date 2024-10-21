-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema shomya
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shomya
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `shomya`;
CREATE SCHEMA IF NOT EXISTS `shomya` DEFAULT CHARACTER SET utf8mb3 ;
USE `shomya` ;

-- -----------------------------------------------------
-- Table `shomya`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hire_date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL unique,
  `image` VARCHAR(200) NULL DEFAULT NULL,
  `is_deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `quantity` INT NOT NULL,
  `description` VARCHAR(200) NULL DEFAULT NULL,
   `image` VARCHAR(200) NULL DEFAULT NULL,
   `is_deleted` BOOLEAN DEFAULT FALSE,
  `admin_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_admin1`
    FOREIGN KEY (`admin_id`)
    REFERENCES `shomya`.`admin` (`id`),
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `shomya`.`category` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `birthdate` DATE NOT NULL,
  `job` VARCHAR(45) NULL DEFAULT NULL,
  `credit_limit` FLOAT NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_date` DATETIME NOT NULL,
  `total_price` FLOAT NOT NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shomya`.`customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`order_item` (
  `quantity` INT NOT NULL,
  `current_price` FLOAT NOT NULL,
  `product_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `order_id`),
  CONSTRAINT `fk_cart_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shomya`.`product` (`id`),
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `shomya`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`customer_has_interests_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`customer_has_interests_category` (
  `customer_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `category_id`),
  CONSTRAINT `fk_customer_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `shomya`.`category` (`id`),
  CONSTRAINT `fk_customer_has_category_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shomya`.`customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`customer_wishlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`customer_wishlist` (
  `customer_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `product_id`),
  CONSTRAINT `fk_customer_has_product_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shomya`.`customer` (`id`),
  CONSTRAINT `fk_customer_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shomya`.`product` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) UNIQUE NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `email` VARCHAR(45) UNIQUE  NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `shomya`.`cart_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shomya`.`cart_item` (
  `product_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`product_id`, `customer_id`),
   CONSTRAINT `fk_product_has_customer_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `shomya`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_customer_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shomya`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
