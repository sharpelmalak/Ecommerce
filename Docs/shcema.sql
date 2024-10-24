-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ecommerce`;
CREATE SCHEMA IF NOT EXISTS `ecommerce` DEFAULT CHARACTER SET utf8mb3 ;
USE `ecommerce` ;

-- -----------------------------------------------------
-- Table `ecommerce`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`admin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hire_date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL unique,
   `image` TINYTEXT NULL DEFAULT NULL,
  `is_deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;



-- -----------------------------------------------------
-- Table `ecommerce`.`payment_method`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`payment_method` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL unique,
  `is_deleted` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `quantity` INT NOT NULL,
  `description` VARCHAR(200) NULL DEFAULT NULL,
   `image` TINYTEXT NULL DEFAULT NULL,
   `is_deleted` BOOLEAN DEFAULT FALSE,
  `admin_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_admin1`
    FOREIGN KEY (`admin_id`)
    REFERENCES `ecommerce`.`admin` (`id`),
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `ecommerce`.`category` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `birthdate` DATE NOT NULL,
  `job` VARCHAR(45) NULL DEFAULT NULL,
   `phone` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;



-- -----------------------------------------------------
-- Table `ecommerce`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_type` VARCHAR(45)  NOT NULL,
  `card_number` VARCHAR(25)  NOT NULL,
  `exp_month` tinyint  NOT NULL,
  `exp_year` smallint  NOT NULL,
   `is_deleted` BOOLEAN DEFAULT FALSE,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_card_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;
-- -----------------------------------------------------
-- Table `ecommerce`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_date` DATETIME NOT NULL,
  `total_price` FLOAT NOT NULL,
   `status` VARCHAR(100) NULL DEFAULT NULL,
  `customer_id` INT NOT NULL,
  `payment_method_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`),
     CONSTRAINT `fk_order_payment_method`
    FOREIGN KEY (`payment_method_id`)
    REFERENCES `ecommerce`.`payment_method` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`order_item` (
  `quantity` INT NOT NULL,
  `current_price` FLOAT NOT NULL,
  `product_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `order_id`),
  CONSTRAINT `fk_cart_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`),
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `ecommerce`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`customer_has_interests_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`customer_has_interests_category` (
  `customer_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `category_id`),
  CONSTRAINT `fk_customer_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `ecommerce`.`category` (`id`),
  CONSTRAINT `fk_customer_has_category_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`customer_wishlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`customer_wishlist` (
  `customer_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `product_id`),
  CONSTRAINT `fk_customer_has_product_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`),
  CONSTRAINT `fk_customer_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) UNIQUE NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `email` VARCHAR(45) UNIQUE  NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ecommerce`.`cart_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`cart_item` (
  `product_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`product_id`, `customer_id`),
   CONSTRAINT `fk_product_has_customer_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_customer_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
