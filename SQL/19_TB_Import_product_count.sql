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
DROP TABLE IF EXISTS `import_product_count`;
CREATE TABLE `import_product_count`  (
  `ipc_id` int(11) NOT NULL AUTO_INCREMENT,
  `ipc_ip_id` int(11) NOT NULL,
  `ipc_count` int(255) DEFAULT NULL,
  PRIMARY KEY (`ipc_id`) USING BTREE,
  KEY `ipc_ip_id` (`ipc_ip_id`),
  CONSTRAINT `ipc_fk_ip` FOREIGN KEY (`ipc_ip_id`) REFERENCES `import_product` (`ip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
