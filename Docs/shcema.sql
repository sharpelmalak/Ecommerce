SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ecommerce
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ecommerce`;
CREATE SCHEMA IF NOT EXISTS `ecommerce` DEFAULT CHARACTER SET utf8mb3;
USE `ecommerce`;

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
  `name` VARCHAR(45) NOT NULL UNIQUE,
  `image` TINYTEXT NULL DEFAULT NULL,
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
  `city` VARCHAR(100) NOT NULL,
  `country` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Table `ecommerce`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_type` VARCHAR(45) NOT NULL,
  `card_number` VARCHAR(25) NOT NULL,
  `exp_month` TINYINT NOT NULL CHECK (exp_month >= 1 AND exp_month <= 12),  -- Month validation
  `exp_year` SMALLINT NOT NULL CHECK (exp_year >= 2024),  -- Year validation
  `cvv` VARCHAR(4) NOT NULL CHECK (LENGTH(cvv) >= 3 AND LENGTH(cvv) <= 4),  -- CVV validation
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
  `status` VARCHAR(100) NOT NULL,  -- Changed to NOT NULL with default values: Placed, Confirmed, Delivered
  `customer_id` INT NOT NULL,
  `shipping_cost` INT,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`))
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
  CONSTRAINT `fk_order_item_product1`
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
  CONSTRAINT `fk_customer_wishlist_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`),
  CONSTRAINT `fk_customer_wishlist_product`
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
  `email` VARCHAR(45) UNIQUE NOT NULL,
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
  CONSTRAINT `fk_cart_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `ecommerce`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cart_item_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ecommerce`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Table `ecommerce`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`payment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,  -- Foreign key to the order table
  `card_id` INT NOT NULL,  -- Foreign key to the card table
  `payment_method` VARCHAR(50) NOT NULL,  -- e.g., 'COD', 'Stripe', 'Fawry'
  `payment_status` VARCHAR(20) NOT NULL,  -- 'Pending', 'Completed', 'Failed'
  `transaction_id` VARCHAR(255),  -- For Stripe/Fawry: external transaction reference
  `amount` DECIMAL(10, 2) NOT NULL,
  `currency` VARCHAR(10),  -- e.g., 'USD', 'EGP'
  `customer_email` VARCHAR(255),  -- For Stripe/Fawry
  `fawry_reference` VARCHAR(255),  -- Fawry specific reference
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_payment_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `ecommerce`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_card`
    FOREIGN KEY (`card_id`)
    REFERENCES `ecommerce`.`card` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Table `ecommerce`.`promotion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ecommerce`.`promotion` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `discount_percentage` DECIMAL(5, 2),  -- Stores discount as a percentage (e.g., 10.00)
  `free_shipping` BOOLEAN NOT NULL,     -- True or False for free shipping
  `country` VARCHAR(100) NULL,          -- Geographic region (e.g., country or city)
  `start_date` DATE NULL,               -- Promotion start date
  `end_date` DATE NULL,                 -- Promotion end date
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;




ALTER TABLE ecommerce.customer
ALTER COLUMN deleted SET DEFAULT false;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
