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
-- Table structure for menu_category
-- ----------------------------
DROP TABLE IF EXISTS `menu_category`;
CREATE TABLE `menu_category` (
  `mc_id` int(11) NOT NULL AUTO_INCREMENT,
  `mc_name` varchar(255) NOT NULL,
  `mc_enable` int(11) NOT NULL,
  PRIMARY KEY (`mc_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;