/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : brightdreams

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 02/11/2023 14:52:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_binding
-- ----------------------------
DROP TABLE IF EXISTS `t_binding`;
CREATE TABLE `t_binding`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CHILD_ID` int NULL DEFAULT NULL,
  `VOLUNTEER_ID` int NULL DEFAULT NULL,
  `IS_DELETED` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'This table shows the binding relationship between child and volunteer.' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_binding
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
