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

 Date: 07/08/2017 11:22:32
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for MenuQueue
-- ----------------------------
DROP TABLE IF EXISTS `menu_queue`;
CREATE TABLE `menu_queue`  (
  `mq_id` int(11) NOT NULL AUTO_INCREMENT,
  `mq_oi_id` int(11) NOT NULL,
  PRIMARY KEY (`mq_id`) USING BTREE,
  UNIQUE INDEX `uq_menu_item`(`mq_oi_id`) USING BTREE,
  CONSTRAINT `fk_mq_oi` FOREIGN KEY (`mq_oi_id`) REFERENCES `order_item` (`order_item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
