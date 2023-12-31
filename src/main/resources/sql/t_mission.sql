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

 Date: 07/11/2023 23:22:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_mission
-- ----------------------------
DROP TABLE IF EXISTS `t_mission`;
CREATE TABLE `t_mission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `mission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `kind` tinyint NULL DEFAULT NULL,
  `reward` int NULL DEFAULT NULL,
  `deadline` datetime NULL DEFAULT NULL,
  `release_date` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint NULL DEFAULT NULL,
  `picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_released` bit(1) NULL DEFAULT NULL,
  `target_num` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
