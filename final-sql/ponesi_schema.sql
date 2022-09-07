-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ponesi
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ponesi
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ponesi` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `ponesi` ;

-- -----------------------------------------------------
-- Table `ponesi`.`restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `wait_time` VARCHAR(20) NULL,
  `address` VARCHAR(100) NOT NULL,
  `has_takeout` TINYINT NOT NULL DEFAULT 0,
  `accepting_orders` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `restaurant_name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`restaurant_has_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`restaurant_has_category` (
  `restaurant_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`restaurant_id`, `category_id`),
  INDEX `fk_restaurant_has_category_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_restaurant_has_category_restaurant_idx` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `fk_restaurant_has_category_restaurant`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `ponesi`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `ponesi`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(64) NOT NULL COMMENT 'Assuming bcrypr is used for passwords.',
  `address` VARCHAR(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ponesi`.`order_payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`order_payment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`courier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`courier` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`delivery_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`delivery_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'For now, it\'s either \"Car\", \"Bicycle\" or \"Moped\", other options may become availabe. (Drone? Pidgeon?)';


-- -----------------------------------------------------
-- Table `ponesi`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `order_payment_id` INT NULL,
  `order_address` VARCHAR(100) NULL,
  `courier_id` INT NOT NULL,
  `delivery_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_order_payment1_idx` (`order_payment_id` ASC) VISIBLE,
  INDEX `fk_order_courier1_idx` (`courier_id` ASC) VISIBLE,
  INDEX `fk_order_delivery_type1_idx` (`delivery_type_id` ASC) VISIBLE,
  UNIQUE INDEX `order_payment_id_UNIQUE` (`order_payment_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ponesi`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_order_payment1`
    FOREIGN KEY (`order_payment_id`)
    REFERENCES `ponesi`.`order_payment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_courier1`
    FOREIGN KEY (`courier_id`)
    REFERENCES `ponesi`.`courier` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_delivery_type1`
    FOREIGN KEY (`delivery_type_id`)
    REFERENCES `ponesi`.`delivery_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `options_shown` SMALLINT NOT NULL,
  `options_chosen` SMALLINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_review_order1_idx` (`order_id` ASC) VISIBLE,
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `ponesi`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`item_kind`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`item_kind` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `restaurant_id` INT NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `description` VARCHAR(500) NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `item_kind_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_item_restaurant1_idx` (`restaurant_id` ASC) VISIBLE,
  INDEX `fk_item_item_kind1_idx` (`item_kind_id` ASC) VISIBLE,
  CONSTRAINT `fk_item_restaurant1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `ponesi`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_item_kind1`
    FOREIGN KEY (`item_kind_id`)
    REFERENCES `ponesi`.`item_kind` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`extra_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`extra_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `max_choices` TINYINT NOT NULL DEFAULT 1,
  `min_choices` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'Also from Glovo.\nThey\'ve grouped together extras so you have \"Sizes\" which has 2-3-5 options, then you have other actuall extras, and then you have stuff like \"utensils\" and \"spicy/mild\" things.';


-- -----------------------------------------------------
-- Table `ponesi`.`item_extra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`item_extra` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `extra_group_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `additional_cost` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_item_extra_extra_group1_idx` (`extra_group_id` ASC) VISIBLE,
  CONSTRAINT `fk_item_extra_extra_group1`
    FOREIGN KEY (`extra_group_id`)
    REFERENCES `ponesi`.`extra_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Stolen from Glovo.\nWhen you open up an item, it\'ll offer \"extras\" which is where they represent additional pricing based on sizes and actuall extras (vegetables, sauces, fries...)\n';


-- -----------------------------------------------------
-- Table `ponesi`.`item_has_item_extra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`item_has_item_extra` (
  `item_id` INT NOT NULL,
  `item_extra_id` INT NOT NULL,
  PRIMARY KEY (`item_id`, `item_extra_id`),
  INDEX `fk_item_has_item_extra_item_extra1_idx` (`item_extra_id` ASC) VISIBLE,
  INDEX `fk_item_has_item_extra_item1_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `fk_item_has_item_extra_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `ponesi`.`item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_has_item_extra_item_extra1`
    FOREIGN KEY (`item_extra_id`)
    REFERENCES `ponesi`.`item_extra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `item_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  `ordered_item_price` DECIMAL(10,2) NULL,
  `quantity` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_item1_idx` (`item_id` ASC) VISIBLE,
  INDEX `fk_order_item_order1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_item1`
    FOREIGN KEY (`item_id`)
    REFERENCES `ponesi`.`item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `ponesi`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`order_item_has_item_extra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`order_item_has_item_extra` (
  `order_item_id` INT NOT NULL,
  `item_extra_id` INT NOT NULL,
  `ordered_extra_price` DECIMAL(10,2) NOT NULL,
  `ordered_extra_quantity` SMALLINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`order_item_id`, `item_extra_id`),
  INDEX `fk_order_item_has_item_extra_item_extra1_idx` (`item_extra_id` ASC) VISIBLE,
  INDEX `fk_order_item_has_item_extra_order_item1_idx` (`order_item_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_has_item_extra_order_item1`
    FOREIGN KEY (`order_item_id`)
    REFERENCES `ponesi`.`order_item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_has_item_extra_item_extra1`
    FOREIGN KEY (`item_extra_id`)
    REFERENCES `ponesi`.`item_extra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`cash_payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`cash_payment` (
  `order_payment_id` INT NOT NULL,
  `user_has_amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`order_payment_id`),
  CONSTRAINT `fk_cash_payment_payment_method1`
    FOREIGN KEY (`order_payment_id`)
    REFERENCES `ponesi`.`order_payment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `shorthand` VARCHAR(5) NOT NULL,
  `full_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `shortname_UNIQUE` (`shorthand` ASC) VISIBLE,
  UNIQUE INDEX `full_name_UNIQUE` (`full_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`credit_card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`credit_card` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '	',
  `name` VARCHAR(45) NOT NULL COMMENT 'Card owner\'s name, not card name, card name as in \"I\'ll call this card Vizica\" is what credit_card.label is for.',
  `card_number` VARCHAR(20) NOT NULL,
  `exp_date` VARCHAR(5) NOT NULL,
  `cvc` VARCHAR(4) NOT NULL,
  `country_id` INT NOT NULL,
  `label` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_credit_card_country1_idx` (`country_id` ASC) VISIBLE,
  INDEX `fk_credit_card_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_credit_card_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `ponesi`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_credit_card_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ponesi`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`cc_payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`cc_payment` (
  `order_payment_id` INT NOT NULL,
  `credit_card_id` INT NOT NULL,
  PRIMARY KEY (`order_payment_id`),
  INDEX `fk_cc_payment_credit_card1_idx` (`credit_card_id` ASC) VISIBLE,
  CONSTRAINT `fk_cc_payment_payment_method1`
    FOREIGN KEY (`order_payment_id`)
    REFERENCES `ponesi`.`order_payment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cc_payment_credit_card1`
    FOREIGN KEY (`credit_card_id`)
    REFERENCES `ponesi`.`credit_card` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`order_has_order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`order_has_order_status` (
  `order_id` INT NOT NULL,
  `order_status_id` INT NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`, `order_status_id`),
  INDEX `fk_order_has_order_status_order_status1_idx` (`order_status_id` ASC) VISIBLE,
  INDEX `fk_order_has_order_status_order1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_has_order_status_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `ponesi`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_order_status_order_status1`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `ponesi`.`order_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ponesi`.`working_hours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ponesi`.`working_hours` (
  `restaurant_id` INT NOT NULL,
  `day` ENUM('Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun', 'Mon-Fri', 'Mon-Sat', 'Mon-Sun') NOT NULL,
  `from` TIME NOT NULL DEFAULT '07:00',
  `to` TIME NOT NULL DEFAULT '22:00',
  PRIMARY KEY (`restaurant_id`),
  CONSTRAINT `fk_working_hours_restaurant1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `ponesi`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

