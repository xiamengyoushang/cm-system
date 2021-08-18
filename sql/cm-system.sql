# CREATE DATABASE `cm_system`;

USE `cm_system`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` varchar(20) NOT NULL COMMENT '用户ID',
    `state` integer NOT NULL DEFAULT 1 COMMENT '用户状态:0=禁用,1=启用',
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
    `head_img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像图片地址',
    `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    `salt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码加盐',
    `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
    `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `edited` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `editor` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

BEGIN;
INSERT INTO `user` VALUES (1, 1, 'admin', '', '', '', '99f0de63863a8dae2f57477a5d26936a', '99603ac0742f992451de21a144ec9ce3', '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
COMMIT;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
    `id` varchar(20) NOT NULL COMMENT '账号ID',
    `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
    `open_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号,如手机号等',
    `category` integer NOT NULL DEFAULT 0 COMMENT '账号类别, 0:用户名+密码',
    `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `edited` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `editor` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_member_id`(`user_id`) USING BTREE COMMENT '普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账号' ROW_FORMAT = Dynamic;

BEGIN;
INSERT INTO `account` VALUES (1, 1, 'admin', 0, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
COMMIT;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色唯一CODE代码',
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
    `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色介绍',
    `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `edited` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `editor` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `code`(`code`) USING BTREE COMMENT '权限CODE代码'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

BEGIN;
INSERT INTO `role` VALUES (1, 'admin', '超级管理员', '管理其他用户、系统和KEY', '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
INSERT INTO `role` VALUES (2, 'operator', 'KEY操作员', '操作和发行KEY', '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
COMMIT;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限唯一CODE代码',
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
    `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限介绍',
    `category` tinyint(1) NULL DEFAULT NULL COMMENT '权限类别',
    `uri` bigint(20) NULL DEFAULT NULL COMMENT 'URL规则',
    `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `edited` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `editor` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `code`(`code`) USING BTREE COMMENT '权限CODE代码'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

BEGIN;
INSERT INTO `permission` VALUES (1, 'user_mng', '用户管理', '管理其他用户和系统', 0, 0, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
INSERT INTO `permission` VALUES (2, 'key_mng', 'KEY管理', '操作和发行KEY', 0, 0, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
COMMIT;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
    `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
    `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `edited` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `editor` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `member_id`(`user_id`) USING BTREE COMMENT '用户ID',
    INDEX `role_id`(`role_id`) USING BTREE COMMENT '角色ID'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 1, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
COMMIT;

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
    `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限ID',
    `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `edited` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `editor` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `role_id`(`role_id`) USING BTREE COMMENT '角色ID',
    INDEX `permission_id`(`permission_id`) USING BTREE COMMENT '权限ID'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
INSERT INTO `role_permission` VALUES (2, 1, 2, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
INSERT INTO `role_permission` VALUES (3, 2, 2, '2021-03-15 10:55:38', 'admin', '2021-03-15 10:55:38', 'admin', 0);
COMMIT;
