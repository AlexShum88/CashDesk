SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cash_deck
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cash_deck
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cash_deck` DEFAULT CHARACTER SET utf8 ;
USE `cash_deck` ;

-- -----------------------------------------------------
-- Table `cash_deck`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck`.`roles` (
  `id` INT NOT NULL,
  `role` VARCHAR(42) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_deck`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `roles_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `cash_deck`.`roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `cash_deck`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(42) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0 check(price>=0),
  `number` DECIMAL(10,2) NOT NULL DEFAULT 0 check(number>=0),
  `is_deleted` TINYINT NULL DEFAULT 0,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_deck`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DECIMAL(20,2) NULL DEFAULT 0,
  `is_canseled` TINYINT NULL DEFAULT 0,
  `date` VARCHAR(42) NOT NULL DEFAULT 'no date',
  `user_id` INT NOT NULL,
  `cansel_autor` INT NULL DEFAULT 0,
  `is_closed` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_checks_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cash_deck`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_deck`.`transaction_has_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck`.`transaction_has_products` (
  `transaction_id` INT NOT NULL,
  `products_id` INT NOT NULL,
  `number` DECIMAL(10,2) NOT NULL DEFAULT 0  check(number>=0),
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0 check(price>=0),
  `current_price` DECIMAL(20,2) NOT NULL DEFAULT 0 check(current_price>=0),
  PRIMARY KEY (`transaction_id`, `products_id`),
  CONSTRAINT `fk_transaction_has_products_transaction1`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `cash_deck`.`transaction` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_has_products_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `cash_deck`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `cash_deck`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `cash_deck`;
INSERT INTO `cash_deck`.`roles` (`id`, `role`) VALUES (1, 'admin');
INSERT INTO `cash_deck`.`roles` (`id`, `role`) VALUES (2, 'merchandiser');
INSERT INTO `cash_deck`.`roles` (`id`, `role`) VALUES (3, 'cashier');
INSERT INTO `cash_deck`.`roles` (`id`, `role`) VALUES (4, 'senior_cashier');
INSERT INTO `cash_deck`.`roles` (`id`, `role`) VALUES (5, 'guest');

COMMIT;


-- -----------------------------------------------------
-- Data for table `cash_deck`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `cash_deck`;
INSERT INTO `cash_deck`.`users` (`id`, `username`, `password`, `roles_id`) VALUES (1, 'root', 'root', 1);
INSERT INTO `cash_deck`.`users` (`id`, `username`, `password`, `roles_id`) VALUES (4, 'qweqwe', 'qweqwe', 2);
INSERT INTO `cash_deck`.`users` (`id`, `username`, `password`, `roles_id`) VALUES (2, 'asdasd', 'asdasd', 3);
INSERT INTO `cash_deck`.`users` (`id`, `username`, `password`, `roles_id`) VALUES (3, '123123', '123123', 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `cash_deck`.`products`
-- -----------------------------------------------------
START TRANSACTION;
USE `cash_deck`;
INSERT INTO `cash_deck`.`products` (`id`, `name`, `price`, `number`, `is_deleted`) VALUES (1, 'fish', 13, 200, 0);
INSERT INTO `cash_deck`.`products` (`id`, `name`, `price`, `number`, `is_deleted`) VALUES (2, 'meat', 15, 100, 0);

COMMIT;



-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cash_deck_test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cash_deck_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cash_deck_test` DEFAULT CHARACTER SET utf8 ;
USE `cash_deck_test` ;

-- -----------------------------------------------------
-- Table `cash_deck_test`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck_test`.`roles` (
  `id` INT NOT NULL,
  `role` VARCHAR(42) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_deck_test`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck_test`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `roles_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `cash_deck_test`.`roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `cash_deck_test`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck_test`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(42) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0 check(price>=0),
  `number` DECIMAL(10,2) NOT NULL DEFAULT 0 check(number>=0),
  `is_deleted` TINYINT NULL DEFAULT 0,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_deck_test`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck_test`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DECIMAL(20,2) NULL DEFAULT 0,
  `is_canseled` TINYINT NULL DEFAULT 0,
  `date` VARCHAR(42) NOT NULL DEFAULT 'no date',
  `user_id` INT NOT NULL,
  `cansel_autor` INT NULL DEFAULT 0,
  `is_closed` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_checks_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cash_deck_test`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_deck_test`.`transaction_has_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cash_deck_test`.`transaction_has_products` (
  `transaction_id` INT NOT NULL,
  `products_id` INT NOT NULL,
  `number` DECIMAL(10,2) NOT NULL DEFAULT 0  check(number>=0),
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0 check(price>=0),
  `current_price` DECIMAL(20,2) NOT NULL DEFAULT 0 check(current_price>=0),
  PRIMARY KEY (`transaction_id`, `products_id`),
  CONSTRAINT `fk_transaction_has_products_transaction1`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `cash_deck_test`.`transaction` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_has_products_products1`
    FOREIGN KEY (`products_id`)
    REFERENCES `cash_deck_test`.`products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `cash_deck_test`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `cash_deck_test`;
INSERT INTO `cash_deck_test`.`roles` (`id`, `role`) VALUES (1, 'admin');
INSERT INTO `cash_deck_test`.`roles` (`id`, `role`) VALUES (2, 'merchandiser');
INSERT INTO `cash_deck_test`.`roles` (`id`, `role`) VALUES (3, 'cashier');
INSERT INTO `cash_deck_test`.`roles` (`id`, `role`) VALUES (4, 'senior_cashier');
INSERT INTO `cash_deck_test`.`roles` (`id`, `role`) VALUES (5, 'guest');

COMMIT;


-- -----------------------------------------------------
-- Data for table `cash_deck_test`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `cash_deck_test`;
INSERT INTO `cash_deck_test`.`users` (`id`, `username`, `password`, `roles_id`) VALUES (1, 'root', 'root', 1);


COMMIT;


-- -----------------------------------------------------
-- Data for table `cash_deck_test`.`products`
-- -----------------------------------------------------
START TRANSACTION;
USE `cash_deck_test`;
INSERT INTO `cash_deck_test`.`products` (`id`, `name`, `price`, `number`, `is_deleted`) VALUES (1, 'fish', 1, 200, 0);
INSERT INTO `cash_deck_test`.`products` (`id`, `name`, `price`, `number`, `is_deleted`) VALUES (2, 'meat', 15, 100, 0);

COMMIT;

