/*
 Navicat Premium Data Transfer

 Source Server         : mysql-云昭云耀
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3307
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 24/07/2023 10:45:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application_photo
-- ----------------------------
DROP TABLE IF EXISTS `application_photo`;
CREATE TABLE `application_photo`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '申请书id',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请书保存地址',
  `user_id` int NOT NULL COMMENT '与报名信息表id相关联',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `application_photo_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `register_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '申请书表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for execution_log
-- ----------------------------
DROP TABLE IF EXISTS `execution_log`;
CREATE TABLE `execution_log`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sub_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `biz_no` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `operator` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fail` tinyint(1) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `extra` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `code_variable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for interview_position
-- ----------------------------
DROP TABLE IF EXISTS `interview_position`;
CREATE TABLE `interview_position`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '面试地点id\r\n',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '面试地点',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '结束时间',
  `capacity` int NOT NULL COMMENT '该地点一小时内可容纳人数',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '面试地点表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for interview_record
-- ----------------------------
DROP TABLE IF EXISTS `interview_record`;
CREATE TABLE `interview_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '面试id',
  `user_id` int NOT NULL COMMENT '参加面试的用户id',
  `faceback` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '面试情况反馈',
  `score` decimal(5, 2) NULL DEFAULT NULL COMMENT '面试得分',
  `interviewer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '面试官',
  `is_passed` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否通过面试 0 未通过 1 通过',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `interview_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '面试记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lecture
-- ----------------------------
DROP TABLE IF EXISTS `lecture`;
CREATE TABLE `lecture`  (
  `lecture_id` int NOT NULL AUTO_INCREMENT COMMENT '宣讲会id',
  `speaker` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '宣讲人',
  `Lecture_theme` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '宣讲主题',
  `Lecture_time` datetime(0) NOT NULL COMMENT '宣讲时间',
  `ticket_number` int NOT NULL COMMENT '参会人数',
  `ticket_remain` int NOT NULL COMMENT '剩余票数',
  `lecture_order` int NOT NULL COMMENT '宣讲会排序',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`lecture_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '宣讲会表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lecture_ticket
-- ----------------------------
DROP TABLE IF EXISTS `lecture_ticket`;
CREATE TABLE `lecture_ticket`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '抢票记录id',
  `user_id` int NOT NULL COMMENT '抢票用户的id',
  `lecture_id` int NOT NULL COMMENT '宣讲会的id',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '抢票记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for register_info
-- ----------------------------
DROP TABLE IF EXISTS `register_info`;
CREATE TABLE `register_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '报名信息id',
  `user_id` int NOT NULL COMMENT '与user表id相关联',
  `direction` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报名方向',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `register_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '报名信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '预约记录id',
  `interview_time` datetime(0) NOT NULL COMMENT '预约面试时间',
  `user_id` int NOT NULL COMMENT '预约面试的用户id',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预约面试表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `is_lectured` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否参加过宣讲会',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专业',
  `academy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '书院',
  `student_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `QQ` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'QQ号',
  `qr_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '二维码',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gender` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
