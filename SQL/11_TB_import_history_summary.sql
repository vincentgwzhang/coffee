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

 Date: 08/08/2017 11:56:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for import_history_summary
-- ----------------------------
DROP TABLE IF EXISTS `import_history_summary`;
CREATE TABLE `import_history_summary`  (
  `ihs_id` int(11) NOT NULL AUTO_INCREMENT,
  `ihs_price` decimal(10, 2) NOT NULL,
  `ihs_time` date NOT NULL,
  PRIMARY KEY (`ihs_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
