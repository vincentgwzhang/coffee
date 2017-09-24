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

 Date: 08/08/2017 11:05:39
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for import_product
-- ----------------------------
DROP TABLE IF EXISTS `import_product`;
CREATE TABLE `import_product`  (
  `ip_id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_countable` int(11) NOT NULL,
  `ip_cn_name` varchar(255) NOT NULL,
  `ip_en_name` varchar(255) NOT NULL,
  `ip_sp_name` varchar(255) NOT NULL,
  PRIMARY KEY (`ip_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
