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

Date: 2017-07-28 01:42:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu_item
-- ----------------------------
DROP TABLE IF EXISTS `menu_item`;
CREATE TABLE `menu_item` (
  `mi_id` int(11) NOT NULL AUTO_INCREMENT,
  `mi_mc_id` int(11) NOT NULL,
  `mi_pic` varchar(512) NOT NULL,
  `mi_enable` int(11) NOT NULL,
  `mi_price` decimal(7,2) NOT NULL,
  `mi_to_chief` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`mi_id`),
  KEY `mi_fk_mc` (`mi_mc_id`),
  CONSTRAINT `mi_fk_mc` FOREIGN KEY (`mi_mc_id`) REFERENCES `menu_category` (`mc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;