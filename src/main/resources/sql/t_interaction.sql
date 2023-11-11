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

 Date: 11/11/2023 19:11:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_interaction
-- ----------------------------
DROP TABLE IF EXISTS `t_interaction`;
CREATE TABLE `t_interaction`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `SENDER_ID` int NULL DEFAULT NULL,
  `RECEIVER_ID` int NULL DEFAULT NULL,
  `TYPE` tinyint NULL DEFAULT NULL,
  `INTERACT_TIME` datetime NULL DEFAULT NULL,
  `AMOUNT` int NULL DEFAULT NULL,
  `CONTENT` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `PICTURE_URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
