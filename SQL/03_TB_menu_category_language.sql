use coffee;

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : coffee

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2017-07-23 16:49:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu_category_language
-- ----------------------------
DROP TABLE IF EXISTS `menu_category_language`;
CREATE TABLE `menu_category_language` (
  `mcl_id` int(11) NOT NULL AUTO_INCREMENT,
  `mcl_mc_id` int(11) NOT NULL,
  `mcl_language` varchar(255) NOT NULL,
  `mcl_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mcl_id`),
  KEY `fk_mcl_mc_id` (`mcl_mc_id`),
  CONSTRAINT `fk_mcl_mc_id` FOREIGN KEY (`mcl_mc_id`) REFERENCES `menu_category` (`mc_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;