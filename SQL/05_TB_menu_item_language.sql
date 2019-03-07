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

Date: 2017-07-28 00:45:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu_item_language
-- ----------------------------
DROP TABLE IF EXISTS `menu_item_language`;
CREATE TABLE `menu_item_language` (
  `mil_id` int(11) NOT NULL AUTO_INCREMENT,
  `mil_mi_id` int(11) NOT NULL,
  `mil_language` varchar(255) NOT NULL,
  `mil_description` varchar(255) NOT NULL,
  PRIMARY KEY (`mil_id`),
  KEY `mil_fk_mi` (`mil_mi_id`),
  CONSTRAINT `mil_fk_mi` FOREIGN KEY (`mil_mi_id`) REFERENCES `menu_item` (`mi_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;