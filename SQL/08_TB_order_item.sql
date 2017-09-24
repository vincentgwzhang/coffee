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

Date: 2017-07-30 18:18:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `order_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_item_order_id` int(11) NOT NULL,
  `order_item_menu_item_id` int(11) NOT NULL,
  `order_item_count` int(11) NOT NULL DEFAULT '0',
  `order_item_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_item_end_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '有些商品是不经过厨师的，这种商品很难得到结束时间，例如客户要的是可乐等',
  `order_item_status` varchar(255) NOT NULL COMMENT 'OPEN:  in chef queue == PROGRESS: chef already made == DONE: chef complete == CANCELED: customer canceled',
  PRIMARY KEY (`order_item_id`),
  KEY `fk_order_items_menu_item` (`order_item_menu_item_id`),
  KEY `fk_order_items_orders` (`order_item_order_id`),
  CONSTRAINT `fk_order_items_menu_item` FOREIGN KEY (`order_item_menu_item_id`) REFERENCES `menu_item` (`mi_id`),
  CONSTRAINT `fk_order_items_orders` FOREIGN KEY (`order_item_order_id`) REFERENCES `orders` (`order_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;