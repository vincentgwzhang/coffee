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

 Date: 20/08/2017 10:08:15
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_item_adjustment
-- ----------------------------
DROP TABLE IF EXISTS `order_item_adjustment`;
CREATE TABLE `order_item_adjustment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `original_order_item_id` int(11) NOT NULL,
  `order_adjustment_id` int(11) NOT NULL,
  `original_price` decimal(7, 2) NOT NULL,
  `original_count` int(255) NOT NULL,
  `adjust_price` decimal(7, 2) NOT NULL,
  `adjust_count` int(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `oia_oi_fk`(`original_order_item_id`) USING BTREE,
  INDEX `oia_oa_fk`(`order_adjustment_id`) USING BTREE,
  CONSTRAINT `oia_oa_fk` FOREIGN KEY (`order_adjustment_id`) REFERENCES `order_item_adjustment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `oia_oi_fk` FOREIGN KEY (`original_order_item_id`) REFERENCES `order_item` (`order_item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
