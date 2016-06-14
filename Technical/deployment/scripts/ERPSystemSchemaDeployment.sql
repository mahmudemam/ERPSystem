-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ERPSystem
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ERPSystem
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ERPSystem` DEFAULT CHARACTER SET utf8 ;
USE `ERPSystem` ;

-- -----------------------------------------------------
-- Table `ERPSystem`.`Job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`Job` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`Employee` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NOT NULL,
  `SecondName` VARCHAR(45) NOT NULL,
  `Phone` BIGINT(20) NOT NULL,
  `NationalID` BIGINT(20) NOT NULL,
  `Address` VARCHAR(45) NULL,
  `Job_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `Job_ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `Phone_UNIQUE` (`Phone` ASC),
  UNIQUE INDEX `NationalID_UNIQUE` (`NationalID` ASC),
  INDEX `fk_Employee_Job_idx` (`Job_ID` ASC),
  CONSTRAINT `fk_Employee_Job`
    FOREIGN KEY (`Job_ID`)
    REFERENCES `ERPSystem`.`Job` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`RawMaterial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`RawMaterial` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(45) NOT NULL,
  `Quantity` INT(11) NOT NULL,
  `Price` INT(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ERPSystem`.`EmployeeUsesRawMaterial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`EmployeeUsesRawMaterial` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Employee_ID` INT NOT NULL,
  `RawMaterial_ID` INT NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`ID`, `Employee_ID`, `RawMaterial_ID`),
  INDEX `fk_Employee_has_RawMaterial_RawMaterial1_idx` (`RawMaterial_ID` ASC),
  INDEX `fk_Employee_has_RawMaterial_Employee1_idx` (`Employee_ID` ASC),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  CONSTRAINT `fk_Employee_has_RawMaterial_Employee1`
    FOREIGN KEY (`Employee_ID`)
    REFERENCES `ERPSystem`.`Employee` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_has_RawMaterial_RawMaterial1`
    FOREIGN KEY (`RawMaterial_ID`)
    REFERENCES `ERPSystem`.`RawMaterial` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`Product` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(45) NOT NULL,
  `Price` INT(11) NOT NULL,
  `Quantity` INT(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`EmployeeProducesProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`EmployeeProducesProduct` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Employee_ID` INT NOT NULL,
  `Product_ID` INT NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`ID`, `Employee_ID`, `Product_ID`),
  INDEX `fk_Employee_has_Product_Product1_idx` (`Product_ID` ASC),
  INDEX `fk_Employee_has_Product_Employee1_idx` (`Employee_ID` ASC),
  CONSTRAINT `fk_Employee_has_Product_Employee1`
    FOREIGN KEY (`Employee_ID`)
    REFERENCES `ERPSystem`.`Employee` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_has_Product_Product1`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `ERPSystem`.`Product` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`Customer` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Phone` BIGINT(20) NOT NULL,
  `City` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC),
  UNIQUE INDEX `Phone_UNIQUE` (`Phone` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`Order` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Date` DATE NOT NULL,
  `Customer_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `Customer_ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  INDEX `fk_Order_Customer1_idx` (`Customer_ID` ASC),
  CONSTRAINT `fk_Order_Customer1`
    FOREIGN KEY (`Customer_ID`)
    REFERENCES `ERPSystem`.`Customer` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ERPSystem`.`OrderItem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ERPSystem`.`OrderItem` (
  `Product_ID` INT NOT NULL,
  `Order_ID` INT NOT NULL,
  `Quantity` INT(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`Product_ID`, `Order_ID`),
  INDEX `fk_Product_has_Order_Order1_idx` (`Order_ID` ASC),
  INDEX `fk_Product_has_Order_Product1_idx` (`Product_ID` ASC),
  CONSTRAINT `fk_Product_has_Order_Product1`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `ERPSystem`.`Product` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_has_Order_Order1`
    FOREIGN KEY (`Order_ID`)
    REFERENCES `ERPSystem`.`Order` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

