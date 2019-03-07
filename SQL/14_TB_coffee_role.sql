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

 Date: 12/08/2017 17:56:37
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coffee_role
-- ----------------------------
DROP TABLE IF EXISTS `coffee_role`;
CREATE TABLE `coffee_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of coffee_role
-- ----------------------------
INSERT INTO `coffee_role` VALUES (1, 'ROLE_OPERATOR');
INSERT INTO `coffee_role` VALUES (2, 'ROLE_COUNTER');
INSERT INTO `coffee_role` VALUES (3, 'ROLE_ADMIN');
INSERT INTO `coffee_role` VALUES (4, 'ROLE_CHIEF');

SET FOREIGN_KEY_CHECKS = 1;
