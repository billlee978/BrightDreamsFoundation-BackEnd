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

 Date: 02/11/2023 19:09:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PASSWORD` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ROLE` tinyint NULL DEFAULT NULL COMMENT '0 represents the admin, 1 represents the child, 2 represents the volunteer, 3 represents the donator.',
  `REALNAME` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `SCHOOL` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `CLAZZ` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `AGE` tinyint NULL DEFAULT NULL,
  `GENDER` tinyint NULL DEFAULT NULL,
  `CREATEDATE` datetime NULL DEFAULT (now()),
  `IS_DELETED` tinyint NULL DEFAULT 0 COMMENT '0 represents not deleted, 1 represents deleted logically.',
  `POINTS` int NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'This table contains the basic information of all users.' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO t_user (username, password, createdate,ROLE) VALUES("admin", "111111", NOW(), 0)
