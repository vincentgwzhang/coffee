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

Date: 2017-08-04 00:35:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for desktop
-- ----------------------------
DROP TABLE IF EXISTS `desktop`;
CREATE TABLE `desktop` (
  `desk_id` int(11) NOT NULL AUTO_INCREMENT,
  `desk_no` int(11) NOT NULL,
  `desk_enable` int(11) NOT NULL COMMENT 'enable means this table could be use',
  `desk_occupied` int(11) NOT NULL DEFAULT '0' COMMENT 'occupied stand for this table has order not closed',
  PRIMARY KEY (`desk_id`),
  UNIQUE KEY `desk_unique` (`desk_no`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

INSERT INTO `desktop` VALUES ('1000', '-1', '1', '0');
-- ----------------------------
-- Records of desktop
-- ----------------------------
INSERT INTO `desktop` VALUES ('1', '1', '1', '0');
INSERT INTO `desktop` VALUES ('2', '2', '1', '0');
INSERT INTO `desktop` VALUES ('3', '3', '1', '0');
INSERT INTO `desktop` VALUES ('4', '4', '1', '0');
INSERT INTO `desktop` VALUES ('5', '5', '1', '0');
INSERT INTO `desktop` VALUES ('6', '6', '1', '0');
INSERT INTO `desktop` VALUES ('7', '7', '1', '0');
INSERT INTO `desktop` VALUES ('8', '8', '1', '0');
INSERT INTO `desktop` VALUES ('9', '9', '1', '0');
INSERT INTO `desktop` VALUES ('10', '10', '1', '0');
INSERT INTO `desktop` VALUES ('11', '11', '1', '0');
INSERT INTO `desktop` VALUES ('12', '12', '1', '0');
INSERT INTO `desktop` VALUES ('13', '13', '1', '0');
INSERT INTO `desktop` VALUES ('14', '14', '1', '0');
INSERT INTO `desktop` VALUES ('15', '15', '1', '0');
INSERT INTO `desktop` VALUES ('16', '16', '1', '0');
INSERT INTO `desktop` VALUES ('17', '17', '1', '0');
INSERT INTO `desktop` VALUES ('18', '18', '1', '0');
INSERT INTO `desktop` VALUES ('19', '19', '1', '0');
INSERT INTO `desktop` VALUES ('20', '20', '1', '0');
INSERT INTO `desktop` VALUES ('21', '21', '1', '0');
INSERT INTO `desktop` VALUES ('22', '22', '1', '0');
INSERT INTO `desktop` VALUES ('23', '23', '1', '0');
INSERT INTO `desktop` VALUES ('24', '24', '1', '0');
INSERT INTO `desktop` VALUES ('25', '25', '1', '0');
INSERT INTO `desktop` VALUES ('26', '26', '1', '0');
INSERT INTO `desktop` VALUES ('27', '27', '1', '0');
INSERT INTO `desktop` VALUES ('28', '28', '1', '0');
INSERT INTO `desktop` VALUES ('29', '29', '1', '0');
INSERT INTO `desktop` VALUES ('30', '30', '1', '0');
INSERT INTO `desktop` VALUES ('31', '31', '1', '0');
INSERT INTO `desktop` VALUES ('32', '32', '1', '0');
INSERT INTO `desktop` VALUES ('33', '33', '1', '0');
INSERT INTO `desktop` VALUES ('34', '34', '1', '0');
INSERT INTO `desktop` VALUES ('35', '35', '1', '0');
INSERT INTO `desktop` VALUES ('36', '36', '1', '0');
INSERT INTO `desktop` VALUES ('37', '37', '1', '0');
INSERT INTO `desktop` VALUES ('38', '38', '1', '0');
INSERT INTO `desktop` VALUES ('39', '39', '1', '0');
INSERT INTO `desktop` VALUES ('40', '40', '1', '0');
INSERT INTO `desktop` VALUES ('41', '41', '1', '0');
