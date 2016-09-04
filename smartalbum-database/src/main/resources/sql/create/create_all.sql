--
-- Script de création du schéma ltr_database.
-- @author sco
-- @date 10/02/2011
-- @version 1.0.0.c
-- TODO : Change old database name, by default is letrader_old 

DROP DATABASE IF EXISTS ltr_database;

CREATE DATABASE ltr_database CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE ltr_database;

--
-- Table Adress
--
DROP TABLE IF EXISTS `ltr_adress`;
CREATE TABLE  `ltr_adress` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adress1` longtext COLLATE utf8_unicode_ci,
  `adress2` longtext COLLATE utf8_unicode_ci,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state_or_region` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `web_site` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `building` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `more_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `way` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `place_called` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Area
--
DROP TABLE IF EXISTS `ltr_area`;
CREATE TABLE  `ltr_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `short_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `area_position` int(5) DEFAULT NULL,
  `segment` bigint(20) DEFAULT NULL,
  `activated` bit(1) DEFAULT 1,
  `deleted` bit(1) DEFAULT 0,
  `area_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'REGION',  
  `latitude` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,  
  `longitude` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `locale_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time_zone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `parent_area_id` bigint(20) DEFAULT NULL,
  `calendar_bgcolor` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `calendar_bgcolor` (`calendar_bgcolor`),
  UNIQUE KEY `short_name` (`short_name`),
  KEY `FK9342C7C2E3C0960` USING BTREE (`parent_area_id`),
  KEY `IDX_LTR_1` USING BTREE (`short_name`),
  CONSTRAINT `FK9342C7C2E3C0960` FOREIGN KEY (`parent_area_id`) REFERENCES `ltr_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Role
--
DROP TABLE IF EXISTS `ltr_role`;
CREATE TABLE  `ltr_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext COLLATE utf8_unicode_ci,
  `employee_role` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Right
--
DROP TABLE IF EXISTS `ltr_right`;
CREATE TABLE  `ltr_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext COLLATE utf8_unicode_ci,
  `employee_right` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Employee
--
DROP TABLE IF EXISTS `ltr_employee`;
CREATE TABLE  `ltr_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact_email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `firstname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL,
  `encoded_password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activated` bit(1) DEFAULT 1,
  `deleted` bit(1) DEFAULT 0, 
  `default_area_id` bigint(20) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  KEY `FKCBCF3B637DAF2F55` USING BTREE (`role_id`),
  KEY `FKCBCF3B63FEE54FF7` USING BTREE (`default_area_id`),
  KEY `IDX_LTR_2` USING BTREE (`login`),
  CONSTRAINT `FKCBCF3B63FEE54FF7` FOREIGN KEY (`default_area_id`) REFERENCES `ltr_area` (`id`),
  CONSTRAINT `FKCBCF3B637DAF2F55` FOREIGN KEY (`role_id`) REFERENCES `ltr_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Employee_Right
--
DROP TABLE IF EXISTS `ltr_employee_right`;
CREATE TABLE `ltr_employee_right` (
  `right_id` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  PRIMARY KEY (`employee_id`,`right_id`),
  KEY `FKA1D40A40F279FE3F` USING BTREE (`right_id`),
  KEY `FKA1D40A401A54BED5` USING BTREE (`employee_id`),
  CONSTRAINT `FKA1D40A401A54BED5` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`),
  CONSTRAINT `FKA1D40A40F279FE3F` FOREIGN KEY (`right_id`) REFERENCES `ltr_right` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Employee_Connection_History
--
DROP TABLE IF EXISTS `ltr_employee_connection_history`;
CREATE TABLE `ltr_employee_connection_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) NOT NULL,
  `connection_date` datetime NOT NULL,
  `ip` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB1975E8F1A54BED5` USING BTREE (`employee_id`),
  CONSTRAINT `FKB1975E8F1A54BED5` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Employee Modification History
--
DROP TABLE IF EXISTS `ltr_employee_modification_history`;
CREATE TABLE `ltr_employee_modification_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) NOT NULL,
  `ip` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `modification_date` datetime NOT NULL,
  `modification_history_action` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK85BFFGR737F0D5` USING BTREE (`employee_id`),
  CONSTRAINT `FK8534FF11D5737F0D5` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Game
--
DROP TABLE IF EXISTS `ltr_game`;
CREATE TABLE `ltr_game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL, 
  `date_end` datetime NOT NULL,
  `date_start` datetime NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `date_created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Company
--
DROP TABLE IF EXISTS `ltr_company`;
CREATE TABLE `ltr_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_created` datetime NOT NULL,
  `adress_id` bigint(20) DEFAULT NULL,
  `deleted` bit(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK4D72FD28A0518495` USING BTREE (`adress_id`),
  CONSTRAINT `FK4D72FD28A0518495` FOREIGN KEY (`adress_id`) REFERENCES `ltr_adress` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Company_Store_Adresse
--
DROP TABLE IF EXISTS `ltr_company_store_adress`;
CREATE TABLE `ltr_company_store_adress` (
  `company_id` bigint(20) NOT NULL,
  `adress_id` bigint(20) NOT NULL,
  PRIMARY KEY (`company_id`,`adress_id`),
  KEY `FKDE596KHGHBFF4AE` USING BTREE (`company_id`),
  KEY `FKDE596FHGYIIOYTG` USING BTREE (`adress_id`),
  CONSTRAINT `FKDEHGGHJJJ7692395` FOREIGN KEY (`company_id`) REFERENCES `ltr_company` (`id`),
  CONSTRAINT `FKDE59POODJDJDJ4AE` FOREIGN KEY (`adress_id`) REFERENCES `ltr_adress` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal
--
DROP TABLE IF EXISTS `ltr_deal`;
CREATE TABLE `ltr_deal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `short_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'CREATED',
  `priority_level` int(10) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,  
  `price` double DEFAULT 0,
  `currency` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `detailed_description_uri` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,  
  `max_quantity_per_user` int(5) DEFAULT 0,
  `max_number_of_buyers` int(10) DEFAULT NULL,
  `min_number_of_buyers` int(10) DEFAULT 0,
  `information_needed_txt` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `need_information` bit(1) DEFAULT 0,
  `need_provider_location` bit(1) DEFAULT 0,
  `need_shipping_adress` bit(1) DEFAULT 0,
  `original_price` double DEFAULT 0,
  `activated` bit(1) DEFAULT 1,
  `silver_point` int(10) DEFAULT 0,
  `gold_point` int(10) DEFAULT 0,
  `platinum_point` int(10) DEFAULT 0,
  `diamond_point` bigint(20) DEFAULT 0,   
  `company_id` bigint(20) DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `margin` double DEFAULT 0,
  `deleted` bit(1) DEFAULT 0,
  `to_head` bit(1) DEFAULT 0,
  `fictive_sale` int(10) DEFAULT 0,
  `need_shipping_fee` bit(1) DEFAULT NULL,
  `shipping_fee` double DEFAULT 0,
  `rich_text_title` text COLLATE utf8_unicode_ci NOT NULL,
  `detailed_description` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `FK9343F3A14D3F695F` USING BTREE (`company_id`),
  KEY `FK9343F3A11A54BED5` USING BTREE (`employee_id`),
  KEY `FKDE596FIEIEIEIIOYTG` USING BTREE (`status`),
  KEY `IDX_LTR_3` USING BTREE (`short_name`),
  CONSTRAINT `FK9343F3A11A54BED5` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`),
  CONSTRAINT `FK9343F3A14D3F695F` FOREIGN KEY (`company_id`) REFERENCES `ltr_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Condition
--
DROP TABLE IF EXISTS `ltr_deal_condition`;
CREATE TABLE `ltr_deal_condition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `condition_txt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `condition_position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Deal_Condition
--
DROP TABLE IF EXISTS `ltr_deal_deal_condition`;
CREATE TABLE `ltr_deal_deal_condition` (
  `deal_id` bigint(20) NOT NULL DEFAULT '0',
  `deal_condition_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`deal_condition_id`),
  KEY `FK4C2A71662F4F2EC6` USING BTREE (`deal_condition_id`),
  KEY `FK4C2A716687692395` USING BTREE (`deal_id`),
  CONSTRAINT `FK4C2A716687692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FK4C2A71662F4F2EC6` FOREIGN KEY (`deal_condition_id`) REFERENCES `ltr_deal_condition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Detail
--
DROP TABLE IF EXISTS `ltr_deal_detail`;
CREATE TABLE `ltr_deal_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `detail_txt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `detail_position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Deal_Detail
--
DROP TABLE IF EXISTS `ltr_deal_deal_detail`;
CREATE TABLE `ltr_deal_deal_detail` (
  `deal_id` bigint(20) NOT NULL,
  `deal_detail_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`deal_detail_id`),
  KEY `FKDE596FE69ABFF4AE` USING BTREE (`deal_detail_id`),
  KEY `FKDE596FE687692395` USING BTREE (`deal_id`),
  CONSTRAINT `FKDE596FE687692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FKDE596FE69ABFF4AE` FOREIGN KEY (`deal_detail_id`) REFERENCES `ltr_deal_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Review
--
DROP TABLE IF EXISTS `ltr_deal_review`;
CREATE TABLE `ltr_deal_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_reviewed` datetime NOT NULL,
  `review_txt` longtext COLLATE utf8_unicode_ci NOT NULL,
  `review_position` int(11) DEFAULT NULL,
  `source` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Deal_Review
--
DROP TABLE IF EXISTS `ltr_deal_deal_review`;
CREATE TABLE `ltr_deal_deal_review` (
  `deal_id` bigint(20) NOT NULL,
  `deal_review_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`deal_review_id`),
  KEY `FKF63E4EED45A2C34E` USING BTREE (`deal_review_id`),
  KEY `FKF63E4EED87692395` USING BTREE (`deal_id`),
  CONSTRAINT `FKF63E4EED87692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FKF63E4EED45A2C34E` FOREIGN KEY (`deal_review_id`) REFERENCES `ltr_deal_review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Picture
--
DROP TABLE IF EXISTS `ltr_deal_picture`;
CREATE TABLE `ltr_deal_picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `picture_position` int(4) DEFAULT NULL,
  `extension` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Deal_Picture
--
DROP TABLE IF EXISTS `ltr_deal_deal_picture`;
CREATE TABLE `ltr_deal_deal_picture` (
  `deal_id` bigint(20) NOT NULL,
  `deal_picture_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`deal_picture_id`),
  KEY `FK6D8BF8091E6CCAE6` USING BTREE (`deal_picture_id`),
  KEY `FK6D8BF80987692395` USING BTREE (`deal_id`),
  CONSTRAINT `FK6D8BF80987692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FK6D8BF8091E6CCAE6` FOREIGN KEY (`deal_picture_id`) REFERENCES `ltr_deal_picture` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Deal_Deal_Adress
--
DROP TABLE IF EXISTS `ltr_deal_adress`;
CREATE TABLE `ltr_deal_adress` (
  `deal_id` bigint(20) NOT NULL,
  `adress_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`adress_id`),
  KEY `FKE0C7660ABDEF86F6` USING BTREE (`adress_id`),
  KEY `FKE0C7660A87692395` USING BTREE (`deal_id`),
  CONSTRAINT `FKE0C7660A87692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FKE0C7660ABDEF86F6` FOREIGN KEY (`adress_id`) REFERENCES `ltr_adress` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Area Deal
--
DROP TABLE IF EXISTS `ltr_area_deal`;
CREATE TABLE `ltr_area_deal` (
  `area_id` bigint(20) NOT NULL,
  `deal_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`area_id`),
  KEY `FKAD776C9FF17A3F5` USING BTREE (`area_id`),
  KEY `FKAD776C987692395` USING BTREE (`deal_id`),
  CONSTRAINT `FKAD776C987692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FKAD776C9FF17A3F5` FOREIGN KEY (`area_id`) REFERENCES `ltr_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table newsletter
--
DROP TABLE IF EXISTS `ltr_newsletter_area`;
DROP TABLE IF EXISTS `ltr_newsletter`;
CREATE TABLE  `ltr_newsletter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



--
-- Table newsletter_area
--
DROP TABLE IF EXISTS `ltr_newsletter_area`;
CREATE TABLE `ltr_newsletter_area` (
  `newsletter_id` bigint(20) NOT NULL,
  `area_id` bigint(20) NOT NULL,
  PRIMARY KEY (`newsletter_id`,`area_id`),
  KEY `FKAD185C9FF17A3F5` USING BTREE (`newsletter_id`),
  KEY `FKAD185C987692395` USING BTREE (`area_id`),
  CONSTRAINT `FKAD185C987692395` FOREIGN KEY (`area_id`) REFERENCES `ltr_area` (`id`),
  CONSTRAINT `FKAD185C9FF17A3F5` FOREIGN KEY (`newsletter_id`) REFERENCES `ltr_newsletter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- Table Configuration
--
DROP TABLE IF EXISTS `ltr_configuration`;
CREATE TABLE `ltr_configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `long_value` longtext COLLATE utf8_unicode_ci DEFAULT NULL,
  `modification_date` datetime NOT NULL, 
  `employee_id` bigint(20) NOT NULL,
  `short_value` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ip` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK9343F3D345F4BED5` USING BTREE (`employee_id`),
  CONSTRAINT `FK9343F3A114353ED5` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`),
  KEY `IDX_LTR_4` USING BTREE (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Table Email_Event
--
DROP TABLE IF EXISTS `ltr_email_event`;
CREATE TABLE `ltr_email_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dest` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_send` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Customer
--
DROP TABLE IF EXISTS `ltr_customer`;
CREATE TABLE  `ltr_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,  
  `email_login` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact_email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `encoded_password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `facebook_id` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `firstname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reward_status` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_customer` varchar(25) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'NOREGISTERED',
  `default_area_id` bigint(20) DEFAULT NULL,
  `sponsor_id` bigint(20) DEFAULT NULL,
  `activated` bit(1) DEFAULT 1,
  `date_created` datetime NOT NULL,
  `deleted` bit(1) DEFAULT 0,
  `total_points_wait` bigint(10) DEFAULT 0,
  `total_points_money` bigint(10) DEFAULT 0,
  `total_points_loyalty` bigint(10) DEFAULT 0,
  `total_points_deleted` bigint(10) DEFAULT 0,
  `employee_id` bigint(20) DEFAULT NULL,
  `title` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `conservation_activated` bit(1) DEFAULT 1,
  `mask_card` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_limitCard` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_login` (`email_login`),
  KEY `FKA8CDD4934F654A6` USING BTREE (`sponsor_id`),
  KEY `FKA8CDD493FEE54FF7` USING BTREE (`default_area_id`),
  KEY `IDX_LTR_5` USING BTREE (`email_login`),
  CONSTRAINT `FKA8CDD493FEE54FF7` FOREIGN KEY (`default_area_id`) REFERENCES `ltr_area` (`id`),
  CONSTRAINT `FKA8CDD4934F654A6` FOREIGN KEY (`sponsor_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FKA8CDD4934F888A6` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Injecter ltr_customer from old to new database
--
INSERT ltr_database.ltr_customer SELECT * FROM letrader_old.ltr_customer;


--
-- Table Customer_Subscribed_Area
--
DROP TABLE IF EXISTS `ltr_customer_subscribed_area`;
CREATE TABLE `ltr_customer_subscribed_area` (
  `customer_id` bigint(20) NOT NULL,
  `area_id` bigint(20) NOT NULL,
  PRIMARY KEY (`customer_id`,`area_id`),
  KEY `FK7B69SFF0518495` USING BTREE (`area_id`),
  KEY `FK7B695ADFE7535805` USING BTREE (`customer_id`),
  CONSTRAINT `FK7B695GDEF7535805` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FK7B695AZEA0518495` FOREIGN KEY (`area_id`) REFERENCES `ltr_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Table Customer_Adress
--
DROP TABLE IF EXISTS `ltr_customer_adress`;
CREATE TABLE `ltr_customer_adress` (
  `customer_id` bigint(20) NOT NULL,
  `adress_id` bigint(20) NOT NULL,
  PRIMARY KEY (`customer_id`,`adress_id`),
  KEY `FK7B695AD2A0518495` USING BTREE (`adress_id`),
  KEY `FK7B695AD2F7535805` USING BTREE (`customer_id`),
  CONSTRAINT `FK7B695AD2F7535805` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FK7B695AD2A0518495` FOREIGN KEY (`adress_id`) REFERENCES `ltr_adress` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Customer Connection History
--
DROP TABLE IF EXISTS `ltr_customer_connection_history`;
CREATE TABLE `ltr_customer_connection_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `connection_date` datetime NOT NULL,
  `ip` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2F50575F5737F0D5` USING BTREE (`customer_id`),
  CONSTRAINT `FK2F50575F5737F0D5` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Customer Modification History
--
DROP TABLE IF EXISTS `ltr_customer_modification_history`;
CREATE TABLE `ltr_customer_modification_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `modification_date` datetime NOT NULL,
  `modification_history_action` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK85BFF11D5737F0D5` USING BTREE (`customer_id`),
  KEY `FK85BFF11D1A54BED5` USING BTREE (`employee_id`),  
  CONSTRAINT `FK85BFF11D5737F0D5` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`),  
  CONSTRAINT `FK85BFF11D1A54BED5` FOREIGN KEY (`employee_id`) REFERENCES `ltr_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Sponsoring
--
DROP TABLE IF EXISTS `ltr_sponsoring`;
CREATE TABLE `ltr_sponsoring` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_accepted` datetime DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `sponsored_user_email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK35F731FD5737F0D5` USING BTREE (`customer_id`),
  KEY `IDX_LTR_3` USING BTREE (`sponsored_user_email`),
  CONSTRAINT `FK35F731FD5737F0D5` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Professional_customer
--
DROP TABLE IF EXISTS `ltr_professional_customer`;
CREATE TABLE `ltr_professional_customer` (
  `id` bigint(20) NOT NULL,
  `company_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK982BC8614D3F695F` USING BTREE (`company_id`),
  CONSTRAINT `FK982BC8614D3F695F` FOREIGN KEY (`company_id`) REFERENCES `ltr_company` (`id`),
  CONSTRAINT `FK982BC861C0D780D4` FOREIGN KEY (`id`) REFERENCES `ltr_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Table gift
--
DROP TABLE IF EXISTS `ltr_gift`;
CREATE TABLE  `ltr_gift` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `to_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `from_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `used` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Table Order
--
DROP TABLE IF EXISTS `ltr_order`;
CREATE TABLE `ltr_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `purchase_user_id` bigint(20) NOT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'UNPAID',
  `payed_by_point` bit(1) DEFAULT 0,
  `ordered_quantity` int(10) NOT NULL,
  `purchased_quantity` int(10) DEFAULT NULL,
  `reward_status` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `ogone_error` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ogone_pay_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `date_created` datetime NOT NULL,
  `iphone` bit(1) DEFAULT 0,
  `gift_id` bigint(20) DEFAULT NULL,
  `information` longtext COLLATE utf8_unicode_ci,      
  `shipping_adress_id` bigint(20) DEFAULT NULL,
  `store_adress_id` bigint(20) DEFAULT NULL,
  `chosen_area_id` bigint(20) DEFAULT NULL,
  `date_cancelled` datetime DEFAULT NULL,
  `ip` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_status_modification` datetime NOT NULL,
  `points_batch` bit(1) DEFAULT 0,
  `agreed_to_cgv` bit(1) DEFAULT 1,
  `code_promo_reduction_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD5DB76B993C892B7` USING BTREE (`store_adress_id`),
  KEY `FKD5DB76B95A073B81` USING BTREE (`chosen_area_id`),
  KEY `FKD5DB76B99DFA7C8A` USING BTREE (`purchase_user_id`),
  KEY `FKD5DB76B45RDFE9B44` USING BTREE (`status`),
  KEY `FKD5DB76B987692395` USING BTREE (`deal_id`),
  KEY `FKD5DB76B987692323` USING BTREE (`gift_id`),
  CONSTRAINT `FKD5DB76B987692323` FOREIGN KEY (`gift_id`) REFERENCES `ltr_gift` (`id`),
  CONSTRAINT `FKD5DB76B987692395` FOREIGN KEY (`deal_id`) REFERENCES `ltr_deal` (`id`),
  CONSTRAINT `FKD5DB76B95A073B81` FOREIGN KEY (`chosen_area_id`) REFERENCES `ltr_area` (`id`),
  CONSTRAINT `FKD5DB76B993C892B7` FOREIGN KEY (`store_adress_id`) REFERENCES `ltr_adress` (`id`),
  CONSTRAINT `FKD5DB76B99DFA7C8A` FOREIGN KEY (`purchase_user_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FKD5DB76B9A0E9B44` FOREIGN KEY (`shipping_adress_id`) REFERENCES `ltr_adress` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Coupon
--
DROP TABLE IF EXISTS `ltr_coupon`;
CREATE TABLE `ltr_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner_user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `date_created` datetime NOT NULL,
  `date_cancelled` datetime DEFAULT NULL,
  `date_expiration` datetime DEFAULT NULL,
  `cancelled` bit(1) DEFAULT 0,
  `secure_code` varchar(20) COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `FKD0F6D61B917E98FF` USING BTREE (`order_id`),
  KEY `FK8EE11DEE737F0D5` USING BTREE (`owner_user_id`),
  CONSTRAINT `FK8DEERE1D5737F0D5` FOREIGN KEY (`owner_user_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FKD0F6D61B917E98FF` FOREIGN KEY (`order_id`) REFERENCES `ltr_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table Code Promo Credits
--
CREATE TABLE `ltr_code_promo_credits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) COLLATE utf8_bin NOT NULL,
  `code_promo_type` varchar(20) COLLATE utf8_bin NOT NULL,
  `credits_points_amount` int(11) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `date_expiration` datetime NOT NULL,
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


--
-- Table Code Promo Reduction
--
CREATE TABLE `ltr_code_promo_reduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `already_used` bit(1) DEFAULT NULL,
  `code` varchar(20) COLLATE utf8_bin NOT NULL,
  `code_promo_type` varchar(20) COLLATE utf8_bin NOT NULL,
  `credits_amount` double NOT NULL,
  `credits_points_amount` int(11) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `date_expiration` datetime NOT NULL,
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Table Ltr_code_promo_credits_customer
--
CREATE TABLE `ltr_code_promo_credits_customer` (
  `code_promo_credit_id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`customer_id`,`code_promo_credit_id`),
  KEY `FK218016D0DD6CB292` (`code_promo_credit_id`) USING BTREE,
  KEY `FK218016D05737F0D5` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


--
-- Table Ltr_code_promo_reduction_deal
--
CREATE TABLE `ltr_code_promo_reduction_deal` (
  `deal_id` bigint(20) NOT NULL,
  `code_promo_reduction_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deal_id`,`code_promo_reduction_id`),
  KEY `FK376F76E5B368C6B5` (`code_promo_reduction_id`) USING BTREE,
  KEY `FK376F76E587692395` (`deal_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Table Ltr_refund
--
CREATE TABLE `ltr_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_refund_process` datetime NOT NULL,
  `date_refund_request` datetime DEFAULT NULL,
  `disagreement_date` datetime DEFAULT NULL,
  `refund_date` datetime DEFAULT NULL,
  `validated` bit(1) DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKE9FBDD6D917E98FF` (`order_id`),
  KEY `FKE9FBDD6D5737F0D5` (`customer_id`),
  CONSTRAINT `FKE9FBDD6D5737F0D5` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FKE9FBDD6D917E98FF` FOREIGN KEY (`order_id`) REFERENCES `ltr_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


--
-- Table Ltr_refund_comment
--
CREATE TABLE `ltr_refund_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime NOT NULL,
  `participant` varchar(255) COLLATE utf8_bin NOT NULL,
  `comment` varchar(255) COLLATE utf8_bin NOT NULL,
  `ltr_refund_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ltr_refund_comment_ibfk_1` (`ltr_refund_id`),
  CONSTRAINT `ltr_refund_comment_ibfk_1` FOREIGN KEY (`ltr_refund_id`) REFERENCES `ltr_refund` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;




--
-- Table Game_Customer
--
DROP TABLE IF EXISTS `ltr_game_customer`;
CREATE TABLE `ltr_game_customer` (
  `game_id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`game_id`,`customer_id`),
  KEY `FK7B695AEGFF38495` USING BTREE (`game_id`),
  KEY `FK7B693FFT645805` USING BTREE (`customer_id`),
  CONSTRAINT `FK7B695LML3335805` FOREIGN KEY (`customer_id`) REFERENCES `ltr_customer` (`id`),
  CONSTRAINT `FK7B695ADDBNEE98495` FOREIGN KEY (`game_id`) REFERENCES `ltr_game` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Table APIKey
--
DROP TABLE IF EXISTS `ltr_apikey`;
CREATE TABLE  `ltr_apikey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apikey` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enabled` bit(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `apikey` (`apikey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Script d'insersion des données statiques.
-- @author sco
-- @date 19/01/20
-- @version 1.0.0.a
USE ltr_database;

DELETE from ltr_right;                                                          
INSERT INTO ltr_right(id,employee_right,description) values (1, 'CAN_ACCESS_APPLICATION', 'authorise l''acces à l''application'),
                                                            (2, 'CAN_USE_EMPLOYEE', 'authorise l''acces à la partie employé'),
                                                            (3, 'CAN_USE_PROFESSIONAL', 'authorise l''acces à la partie pro'),
                                                            (4, 'CAN_USE_DEAL', 'authorise l''acces à la partie deal'),
                                                            (5, 'CAN_USE_REPORTING', 'authorise l''acces à la partie reporting'),
                                                            (6, 'CAN_USE_SELFCARE', 'authorise l''acces à la partie support client'),
                                                            (7, 'CAN_USE_ADMIN', 'authorise l''acces à la partie administration'),
                                                            (8, 'CAN_MODIFY_EMPLOYEE', 'authorise des modifications sur la partie employé'),
                                                            (9, 'CAN_MODIFY_PROFESSIONAL', 'authorise des modifications sur la parte pro'),
                                                            (10, 'CAN_MODIFY_DEAL', 'authorise des modifications sur la partie deal'),
                                                            (11, 'CAN_MODIFY_REPORTING', 'authorise des modifications sur la partie reporting'),
                                                            (12, 'CAN_MODIFY_SELFCARE', 'authorise des modifications sur la partie support client'),
                                                            (13, 'CAN_MODIFY_ADMIN', 'authorise des modifications sur la partie administration'),
                                                            (14, 'CAN_USE_EMPLOYEE_ADMIN', 'authorise l''acces à la partie administration pour un employé'),
                                                            (15, 'CAN_USE_CODE_PROMO', 'authorise l''acces à la partie code promotionnel pour un superadmin');

--  
-- DELETE from ltr_area;                                                            
-- INSERT INTO ltr_area(id,time_zone,locale_code,name,longitude,latitude,short_name,area_position,parent_area_id,area_type,calendar_bgcolor,activated)
-- values (1,'Europe/Paris','fr_Fr','France',NULL,NULL,'france',NULL,NULL,'COUNTRY','f885f7',1),
--       (2,'Europe/Paris','fr_Fr','Paris','2.351074','48.872845','paris',7,1,'TOWN','287654',1),
--       (3,'Europe/Paris','fr_FR','Lille','3.0630174','50.6371834','lille',3,1,'TOWN','b3ee5e',1),
--       (4,'Europe/Paris','fr_Fr','Lyon','2.351074','45.7672990','lyon',4,1,'TOWN','cee24c',1),
--       (5,'Europe/Paris','fr_Fr','Marseille','5.3810421','43.2976116','marseille',5,1,'TOWN','c2c656',1),
--       (6,'Europe/Paris','fr_Fr','Bordeaux','-0.57614','44.83737','bordeaux',2,1,'TOWN','223a1d',1),
--       (7,'Europe/Paris','fr_Fr','Toulouse','1.44295','43.60436','toulouse',11,1,'TOWN','7daec1',1),
--       (8,'Europe/Paris','fr_Fr','Nice','7.26627','43.70343','nice',6,1,'TOWN','82542f',1),
--       (9,'Europe/Paris','fr_Fr','Privilège National',NULL,NULL,'privilege',1,1,'TOWN','83e570',1),
--       (10,'Europe/Paris','fr_Fr','Strasbourg','7.74375','48.58293','strasbourg',10,1,'TOWN','9a33f',1),
--       (11,'Europe/Paris','fr_Fr','Hauts de Seine',NULL,NULL,'haut_de_seine',8,1,'REGION','83eb01',1),
--       (12,'Europe/Paris','fr_Fr','Val de Marne',NULL,NULL,'val_de_marne',9,1,'REGION','e58372',1);

--
-- Script de création des vues ltr_database.
-- @author sco
-- @date 19/01/20
-- @version 1.0.0.a

-- Vue client pro
DROP VIEW IF EXISTS v_professional_customer;
CREATE VIEW v_professional_customer AS select c.* from ltr_professional_customer pc, ltr_customer c where pc.id = c.id;


DROP VIEW IF EXISTS v_customer_history_points;
CREATE VIEW v_customer_history_points AS SELECT c.id as "id utilisateur",
								       o.reward_status as "status au moment de la commande",
								       IF(o.reward_status = 'SILVER', 'SILVER', 'NON') as "points" FROM ltr_customer c, ltr_order o, ltr_deal d
								where o.purchase_user_id = c.id
								and o.deal_id = o.id
								and o.status = 'PAID';
								
DROP VIEW IF EXISTS v_selfcare_coupon;
CREATE VIEW v_selfcare_coupon AS
   SELECT
	 cpn.id AS "id_coupon",
	 cpn.code AS "code_coupon",
	 cpn.date_cancelled is not null AS "annulation_coupon",
     ord.date_created AS "date_achat",
     cpn.date_created AS "date_created",
     CONCAT(customer.firstname,' ',customer.name) AS "nom_client",
     IF(customer.id=cpn.owner_user_id,'', CONCAT(beneficiaire.firstname,' ',beneficiaire.name)) AS "nom_beneficiaire",
     customer.email_login AS "email_client",
     customer.contact_email AS "email_contact_client",
     deal.title AS "titre_tentation",
     company.company_name AS "nom_company"
   FROM ltr_coupon cpn
   LEFT JOIN
   	 ltr_order ord
   ON cpn.order_id=ord.id
   LEFT JOIN
   	 ltr_customer customer
   ON ord.purchase_user_id=customer.id
   LEFT JOIN
   	 ltr_customer beneficiaire
   ON cpn.owner_user_id=beneficiaire.id
   LEFT JOIN
   	 ltr_deal deal
   ON 
   	 ord.deal_id=deal.id
   LEFT JOIN 
   	 ltr_company company
   ON 
	deal.company_id=company.id
	where cpn.owner_user_id is not null;
   	

DROP VIEW IF EXISTS v_selfcare_deal;								
CREATE VIEW v_selfcare_deal AS
	SELECT 
	  ltr_deal.id, 
	  ltr_deal.start_date, 
	  ltr_deal.short_name, 
	  ltr_deal.title, 
	  ltr_deal.min_number_of_buyers, 
	  count(ltr_order.id) AS nb_orders
	FROM
	  ltr_deal 
	LEFT JOIN 
	  ltr_order 
	ON 
	  ltr_deal.id = ltr_order.deal_id
	  WHERE ltr_deal.start_date IS NOT NULL
	GROUP BY 
	  ltr_deal.id, ltr_deal.start_date, ltr_deal.short_name, ltr_deal.title, ltr_deal.min_number_of_buyers;
	  

DROP VIEW IF EXISTS v_ltr_order_max_datecreated;
CREATE VIEW v_ltr_order_max_datecreated as
	select * from ltr_order where date_created=(select Max(ltr_order.date_created) from ltr_order);
	
DROP VIEW IF EXISTS v_selfcare_customer;
CREATE VIEW v_selfcare_customer AS
	SELECT
		c.id as "id",
		c.date_created as "date_created",
		c.name as "name",
		c.firstname as "firstname",
		c.email_login as "email",
		c.contact_email AS "emailcontact",
		ltr_adress.zip_code as "codepostal",
		d.title as "lastdealtitle",
		odr.status as "status"
	FROM
		ltr_customer c
	LEFT JOIN
		v_ltr_order_max_datecreated odr 
	ON
		c.id = odr.purchase_user_id	
	LEFT JOIN
		ltr_deal d
	ON 
		odr.deal_id=d.id
	LEFT JOIN
		(ltr_customer_adress, ltr_adress)
	ON
		(ltr_customer_adress.customer_id=c.id and ltr_customer_adress.adress_id=ltr_adress.id)
	WHERE 
		c.type_customer LIKE 'SIMPLE';

DROP VIEW IF EXISTS v_reporting_deal;
CREATE VIEW v_reporting_deal AS
	SELECT
		d.id as 'deal_id',
		o.chosen_area_id as 'area_id', 
		d.end_date, 
		d.price,
		d.margin,
		o.purchased_quantity
	FROM
		ltr_deal d
		Inner Join
			ltr_order o on d.id = o.deal_id AND o.status = 'PAID'
	;

-- Use for calculate CA of reporting, filter by time (week, mounth, day ...)
-- 
-- @author: xkhe
--

/* For loggging information, useful for reporting */ 
DROP TABLE IF EXISTS ltr_reporting_period;
CREATE TABLE ltr_reporting_period ( 
		Id INT(11) NOT NULL AUTO_INCREMENT, 
		date DATE NOT NULL,
		total DOUBLE,
		margin DOUBLE,
    	nb_sold INT(11),
		PRIMARY KEY (Id) 
); 

DROP PROCEDURE IF EXISTS reporting_filter_by_time;
DELIMITER //

CREATE PROCEDURE reporting_filter_by_time (d date, d2 date, periode INT)
BEGIN

	 DECLARE no_more_products INT DEFAULT 0;
	 DECLARE res VARCHAR(255); 
	 DECLARE start_d, end_d,  max_end_date, min_end_date DATE;
	 DECLARE marge INT DEFAULT 0;  
	 DECLARE nb_sold INT DEFAULT 0; 
	 DECLARE total DOUBLE DEFAULT 0; 
	 DECLARE str_date VARCHAR(255); 

  	SET start_d = d;
	SET max_end_date = d;
	SET res = '';

	DELETE FROM ltr_reporting_period;
	/* Get max, min end date  */
  	SELECT max(v.end_date) INTO max_end_date FROM v_reporting_deal v;
  	SELECT min(v.end_date) INTO min_end_date FROM v_reporting_deal v;
  	IF start_d < min_end_date 
  		THEN SET start_d = min_end_date;
  	END IF;
  	IF d2 is not null and d2 < max_end_date 
  		THEN SET max_end_date = d2;
  	END IF;
  	IF periode = 31 THEN
  		SET end_d = ADDDATE(start_d, interval 1 month);
  	Else
  		SET end_d = ADDDATE(start_d, periode);
  	END IF;
  
	/* Do filter */
	REPEAT 
		SELECT
			start_d AS 'SEMAINE', 
			sum(v.price * v.purchased_quantity) AS 'TOTAL',
			sum(v.margin) AS 'MARGE',
			sum(v.purchased_quantity) AS 'NOMBRE DE VENTE' INTO start_d, total, marge, nb_sold
		FROM v_reporting_deal v
		WHERE v.end_date BETWEEN start_d AND end_d;

		IF total IS NULL 
			THEN SET total = 0;
		END IF;
		INSERT INTO ltr_reporting_period(date, nb_sold, total, margin) VALUES (start_d, nb_sold, total, marge);
		IF periode = 31 THEN
			SET start_d = ADDDATE(start_d, interval 1 month);
  			SET end_d = ADDDATE(end_d, interval 1 month);
  		Else
  			SET start_d = ADDDATE(start_d, periode);
			SET end_d = ADDDATE(end_d, periode);
  		END IF;
			
		IF start_d >= max_end_date 
			THEN SET no_more_products = 1;
		END IF;
		
	UNTIL no_more_products = 1
	END REPEAT;

END //
DELIMITER ;

--
-- Procedure for filter by area with periode 
--
DROP PROCEDURE IF EXISTS reporting_filter_by_time_area;
DELIMITER //

CREATE PROCEDURE reporting_filter_by_time_area (d date, d2 date, periode INT, area_id bigint(20))
BEGIN
	 
	 DECLARE no_more_products INT DEFAULT 0;
	 DECLARE res VARCHAR(255); 
	 DECLARE start_d, end_d,  max_end_date, min_end_date DATE;
	 DECLARE marge INT DEFAULT 0;  
	 DECLARE nb_sold INT DEFAULT 0; 
	 DECLARE total DOUBLE DEFAULT 0; 
	 DECLARE str_date VARCHAR(255);  
	 DECLARE good_area_id bigint(20) DEFAULT 0;

  	SET start_d = d;
	SET max_end_date = d;
	SET res = '';

	DELETE FROM ltr_reporting_period;
   /* Check area id */  
	SELECT count(v.area_id) INTO good_area_id FROM v_reporting_deal v WHERE v.area_id = area_id;

	IF good_area_id > 0 THEN
		/* Get max, min end date  */
	  	SELECT max(v.end_date) INTO max_end_date FROM v_reporting_deal v WHERE v.area_id = area_id;
		SELECT min(v.end_date) INTO min_end_date FROM v_reporting_deal v WHERE v.area_id = area_id;
	  	IF start_d < min_end_date 
	  		THEN SET start_d = min_end_date;
	  	END IF;
	  	IF d2 is not null and d2 < max_end_date 
  			THEN SET max_end_date = d2;
  		END IF;
  		IF periode = 31 THEN
  			SET end_d = ADDDATE(start_d, interval 1 month);
  		Else
  			SET end_d = ADDDATE(start_d, periode);
  		END IF;
		/* Do filter */
		REPEAT 
			SELECT
				start_d AS 'SEMAINE', 
				sum(v.price * v.purchased_quantity) AS 'TOTAL',
				sum(v.margin) AS 'MARGE',
				sum(v.purchased_quantity) AS 'NOMBRE DE VENTE' INTO start_d, total, marge, nb_sold
			FROM v_reporting_deal v
			WHERE v.end_date BETWEEN start_d AND end_d AND v.area_id = area_id;
	
			IF total IS NULL 
				THEN SET total = 0;
			END IF;
			INSERT INTO ltr_reporting_period(date, nb_sold, total, margin) VALUES (start_d, nb_sold, total, marge);
			IF periode = 31 THEN
				SET start_d = ADDDATE(start_d, interval 1 month);
  				SET end_d = ADDDATE(end_d, interval 1 month);
  			Else
  				SET start_d = ADDDATE(start_d, periode);
				SET end_d = ADDDATE(end_d, periode);
  			END IF;
			
			IF start_d >= max_end_date 
				THEN SET no_more_products = 1;
			END IF;
			
		UNTIL no_more_products = 1
		END REPEAT;
	END IF;
END //
DELIMITER ;


--
-- Procedure for filter by deal with periode 
--
DROP PROCEDURE IF EXISTS reporting_filter_by_time_deal;
DELIMITER //

CREATE PROCEDURE reporting_filter_by_time_deal (d date, d2 date, periode INT, deal_id bigint(20))
BEGIN

	 DECLARE no_more_products INT DEFAULT 0;
	 DECLARE res VARCHAR(255); 
	 DECLARE start_d, end_d,  max_end_date, min_end_date DATE;
	 DECLARE marge INT DEFAULT 0;  
	 DECLARE nb_sold INT DEFAULT 0; 
	 DECLARE total DOUBLE DEFAULT 0; 
	 DECLARE str_date VARCHAR(255);  
	 DECLARE good_deal_id bigint(20) DEFAULT 0;

  	SET start_d = d;
	SET max_end_date = d;
	SET res = '';

	DELETE FROM ltr_reporting_period;
  	/* Check deal id */  
	SELECT count(v.deal_id) INTO good_deal_id FROM v_reporting_deal v WHERE v.deal_id = deal_id;

	IF good_deal_id > 0 THEN 
		/* Get max end date  */
		SELECT max(v.end_date) INTO max_end_date FROM v_reporting_deal v WHERE v.deal_id = deal_id;
		SELECT min(v.end_date) INTO min_end_date FROM v_reporting_deal v WHERE v.deal_id = deal_id;
	  	IF start_d < min_end_date 
	  		THEN SET start_d = min_end_date;
	  	END IF;
	  	IF d2 is not null and d2 < max_end_date 
  			THEN SET max_end_date = d2;
  		END IF;
  		IF periode = 31 THEN
  			SET end_d = ADDDATE(start_d, interval 1 month);
  		Else
  			SET end_d = ADDDATE(start_d, periode);
  		END IF;
  		
		/* Do filter */
		REPEAT 
			SELECT
				start_d AS 'SEMAINE', 
				sum(v.price * v.purchased_quantity) AS 'TOTAL',
				sum(v.margin) AS 'MARGE',
				sum(v.purchased_quantity) AS 'NOMBRE DE VENTE' INTO start_d, total, marge, nb_sold
			FROM v_reporting_deal v
			WHERE v.end_date BETWEEN start_d AND end_d AND v.deal_id = deal_id;
			
			IF total IS NULL 
				THEN SET total = 0;
			END IF;
			INSERT INTO ltr_reporting_period(date, nb_sold, total, margin) VALUES (start_d, nb_sold, total, marge);
			IF periode = 31 THEN
				SET start_d = ADDDATE(start_d, interval 1 month);
  				SET end_d = ADDDATE(end_d, interval 1 month);
  			Else
  				SET start_d = ADDDATE(start_d, periode);
				SET end_d = ADDDATE(end_d, periode);
  			END IF;
			
			IF start_d >= max_end_date 
				THEN SET no_more_products = 1;
			END IF;
			
		UNTIL no_more_products = 1
		END REPEAT;
	END IF;

END //
DELIMITER ;


--
-- Procedure for filter by area and deal with periode 
--
DROP PROCEDURE IF EXISTS reporting_filter_by_time_area_deal;
DELIMITER //

CREATE PROCEDURE reporting_filter_by_time_area_deal (d date, d2 date, periode INT, area_id bigint(20), deal_id bigint(20))
BEGIN
	 
	 DECLARE no_more_products INT DEFAULT 0;
	 DECLARE res VARCHAR(255); 
	 DECLARE start_d, end_d,  max_end_date, min_end_date DATE;
	 DECLARE marge INT DEFAULT 0;  
	 DECLARE nb_sold INT DEFAULT 0; 
	 DECLARE total DOUBLE DEFAULT 0; 
	 DECLARE str_date VARCHAR(255);  
	 DECLARE good_deal_id bigint(20) DEFAULT 0; 
	 DECLARE good_area_id bigint(20) DEFAULT 0;

  	SET start_d = d;
	SET max_end_date = d;
	SET res = '';

	DELETE FROM ltr_reporting_period;
  	/* Check deal id and area id */  
	SELECT count(v.area_id), count(v.deal_id) INTO good_area_id, good_deal_id 
	FROM v_reporting_deal v 
	WHERE v.deal_id = deal_id AND v.area_id = area_id;
	
	IF good_deal_id > 0 AND good_area_id > 0 THEN 
		/* Get max end date  */
	  	SELECT max(v.end_date) INTO max_end_date FROM v_reporting_deal v WHERE v.deal_id = deal_id AND v.area_id = area_id;
		SELECT min(v.end_date) INTO min_end_date FROM v_reporting_deal v WHERE v.deal_id = deal_id AND v.area_id = area_id;
  		IF start_d < min_end_date 
  			THEN SET start_d = min_end_date;
  		END IF;
  		IF periode = 31 THEN
  			SET end_d = ADDDATE(start_d, interval 1 month);
  		Else
  			SET end_d = ADDDATE(start_d, periode);
  		END IF;
  		IF d2 is not null and d2 < max_end_date 
  			THEN SET max_end_date = d2;
  		END IF;
  		
		/* Do filter */
		REPEAT 
			SELECT
				start_d AS 'SEMAINE', 
				sum(v.price * v.purchased_quantity) AS 'TOTAL',
				sum(v.margin) AS 'MARGE',
				sum(v.purchased_quantity) AS 'NOMBRE DE VENTE' INTO start_d, total, marge, nb_sold
			FROM v_reporting_deal v
			WHERE v.end_date BETWEEN start_d AND end_d AND v.deal_id = deal_id AND v.area_id = area_id;
	
			
			IF total IS NULL 
				THEN SET total = 0;
			END IF;
			INSERT INTO ltr_reporting_period(date, nb_sold, total, margin) VALUES (start_d, nb_sold, total, marge);
			
			IF periode = 31 THEN
				SET start_d = ADDDATE(start_d, interval 1 month);
  				SET end_d = ADDDATE(end_d, interval 1 month);
  			Else
  				SET start_d = ADDDATE(start_d, periode);
				SET end_d = ADDDATE(end_d, periode);
  			END IF;
			
			IF start_d >= max_end_date 
				THEN SET no_more_products = 1;
			END IF;
			
		UNTIL no_more_products = 1
		END REPEAT;
	END IF;

END //
DELIMITER ;



--
-- Example of use
--
DELETE FROM ltr_reporting_period;
-- CALL reporting_filter_by_time(DATE('2010-06-14'), 7);
-- CALL reporting_filter_by_time_area(DATE('2010-06-14'), null, 30, 2); /* Filter by paris */
-- CALL reporting_filter_by_time_deal(DATE('2010-06-14'), null, 7, 49); /* Filter deal id = 49 */
-- CALL reporting_filter_by_time_area_deal(DATE('2010-06-14'), DATE('2011-06-14'), 7, 2, 49); /* Filter deal id = 49 and paris */
-- CALL reporting_filter_by_time_area_deal(DATE('2010-06-14'), null, 7, 2, 49); /* Filter deal id = 49 and paris */
-- SELECT * FROM ltr_reporting_period; 
