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

 Date: 12/08/2017 17:57:16
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  UNIQUE INDEX `ur_unique`(`user_id`, `role_id`) USING BTREE,
  INDEX `fk_ur_cr`(`role_id`) USING BTREE,
  CONSTRAINT `fk_ur_cr` FOREIGN KEY (`role_id`) REFERENCES `coffee_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ur_cu` FOREIGN KEY (`user_id`) REFERENCES `coffee_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES (1, 1);
INSERT INTO `users_roles` VALUES (2, 2);
INSERT INTO `users_roles` VALUES (3, 3);
INSERT INTO `users_roles` VALUES (4, 4);

SET FOREIGN_KEY_CHECKS = 1;
