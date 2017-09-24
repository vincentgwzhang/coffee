use coffee;

/*
 Navicat Premium Data Transfer

 Source Server         : Self
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : coffee

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 20/08/2017 14:10:20
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_adjustment_overview
-- ----------------------------
DROP TABLE IF EXISTS `order_adjustment_overview`;
CREATE TABLE `order_adjustment_overview`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adjust_date` date NOT NULL,
  `original_total` decimal(7, 2) NOT NULL,
  `adjust_total` decimal(7, 2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `oa_unique`(`adjust_date`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
