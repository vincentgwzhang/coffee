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

 Date: 20/08/2017 10:08:41
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orders_adjustment
-- ----------------------------
DROP TABLE IF EXISTS `orders_adjustment`;
CREATE TABLE `orders_adjustment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `overview_id` int(11) NOT NULL,
  `original_order_id` int(11) NOT NULL,
  `original_price` decimal(7, 2) NOT NULL,
  `updated_price` decimal(7, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `oa_o_fk`(`original_order_id`) USING BTREE,
  INDEX `oa_oao+fk`(`overview_id`) USING BTREE,
  CONSTRAINT `oa_o_fk` FOREIGN KEY (`original_order_id`) REFERENCES `orders` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `oa_oao+fk` FOREIGN KEY (`overview_id`) REFERENCES `order_adjustment_overview` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
