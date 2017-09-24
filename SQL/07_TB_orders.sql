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

Date: 2017-07-29 17:12:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_desktop_no` int(11) DEFAULT NULL COMMENT '-1 stand for take away order',
  `order_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_total_price` decimal(7,2) DEFAULT NULL,
  `order_actual_price` decimal(7,2) DEFAULT NULL,
  `order_type` varchar(255) NOT NULL COMMENT 'OPEN: not clearing == CLOSE: Already close == LOST: customer has not paid',
  PRIMARY KEY (`order_id`),
  KEY `fk_order_desktop` (`order_desktop_no`),
  CONSTRAINT `fk_order_desktop` FOREIGN KEY (`order_desktop_no`) REFERENCES `desktop` (`desk_no`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;