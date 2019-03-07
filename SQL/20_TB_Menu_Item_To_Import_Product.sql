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
DROP TABLE IF EXISTS `menu_item_to_import_product`;
CREATE TABLE `menu_item_to_import_product`  (
  `mitip_id` int(11) NOT NULL AUTO_INCREMENT,
  `mitip_ip_id` int(11) NOT NULL,
  `mitip_mi_id` int(11) NOT NULL,
  PRIMARY KEY (`mitip_id`) USING BTREE,
  KEY `mitip_ip_id` (`mitip_ip_id`),
  KEY `mitip_mi_id` (`mitip_mi_id`),
  CONSTRAINT `mitip_fk_ip` FOREIGN KEY (`mitip_ip_id`) REFERENCES `import_product` (`ip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mitip_fk_mi` FOREIGN KEY (`mitip_mi_id`) REFERENCES `menu_item` (`mi_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
