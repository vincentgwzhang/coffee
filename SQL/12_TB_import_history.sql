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

 Date: 08/08/2017 11:56:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for import_history
-- ----------------------------
DROP TABLE IF EXISTS `import_history`;
CREATE TABLE `import_history`  (
  `ih_id` int(11) NOT NULL AUTO_INCREMENT,
  `ih_ihs_id` int(11) NOT NULL,
  `ih_ip_id` int(11) NOT NULL,
  `ih_price` decimal(7, 2) DEFAULT NULL COMMENT 'total price, not unit price',
  `ih_count` int(255) DEFAULT NULL,
  PRIMARY KEY (`ih_id`) USING BTREE,
  INDEX `fk_ih_ip`(`ih_ip_id`) USING BTREE,
  INDEX `fk_ih_ihs`(`ih_ihs_id`) USING BTREE,
  CONSTRAINT `fk_ih_ip` FOREIGN KEY (`ih_ip_id`) REFERENCES `import_product` (`ip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ih_ihs` FOREIGN KEY (`ih_ihs_id`) REFERENCES `import_history_summary` (`ihs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
