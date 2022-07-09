/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : x_webalbum

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 09/07/2022 15:27:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_folder
-- ----------------------------
DROP TABLE IF EXISTS `file_folder`;
CREATE TABLE `file_folder`  (
  `file_folder_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件夹ID',
  `file_folder_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件夹名称',
  `parent_folder_id` int(11) NULL DEFAULT 0 COMMENT '父文件夹ID',
  `file_store_id` int(11) NULL DEFAULT NULL COMMENT '所属文件仓库ID',
  `file_folder_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件夹路径',
  PRIMARY KEY (`file_folder_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_folder
-- ----------------------------
INSERT INTO `file_folder` VALUES (1, 'wen', 0, 1, 'D:/project-support/x-webalbum/store/1/wen');

-- ----------------------------
-- Table structure for file_store
-- ----------------------------
DROP TABLE IF EXISTS `file_store`;
CREATE TABLE `file_store`  (
  `file_store_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件仓库ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '主人ID',
  `current_size` bigint(20) NULL DEFAULT 0 COMMENT '当前容量（单位KB）',
  `max_size` bigint(20) NULL DEFAULT 1048576 COMMENT '最大容量（单位KB）',
  PRIMARY KEY (`file_store_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_store
-- ----------------------------
INSERT INTO `file_store` VALUES (1, 1, 0, 2097152000);
INSERT INTO `file_store` VALUES (2, 2, 0, 2097152000);
INSERT INTO `file_store` VALUES (3, 4, 0, 2097152000);

-- ----------------------------
-- Table structure for my_file
-- ----------------------------
DROP TABLE IF EXISTS `my_file`;
CREATE TABLE `my_file`  (
  `my_file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `my_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_store_id` int(11) NULL DEFAULT NULL COMMENT '文件仓库ID',
  `my_file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/' COMMENT '文件存储路径',
  `download_count` int(11) NULL DEFAULT 0 COMMENT '下载次数',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更改时间',
  `parent_folder_id` int(11) NULL DEFAULT NULL COMMENT '父文件夹ID',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小（字节）',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`my_file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 426 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of my_file
-- ----------------------------
INSERT INTO `my_file` VALUES (379, '12333.jpg', 1, 'D:/project-support/x-webalbum/store/1/12333.jpg', 0, '2022-06-20 11:39:49', 0, 67037, '图片');
INSERT INTO `my_file` VALUES (380, '123ss文.jpg', 1, 'D:/project-support/x-webalbum/store/1/123ss文.jpg', 0, '2022-06-20 11:39:49', 0, 670318, '图片');
INSERT INTO `my_file` VALUES (381, '123', 1, 'D:/project-support/x-webalbum/store/1/123', 0, '2022-06-20 11:39:49', 0, 2475109, '其他');
INSERT INTO `my_file` VALUES (382, 'IMG_20220614_203209.jpg', 1, 'D:/project-support/x-webalbum/store/1/IMG_20220614_203209.jpg', 0, '2022-06-20 11:39:49', 0, 3052153, '图片');
INSERT INTO `my_file` VALUES (383, 'IMG_20220614_213352.jpg', 1, 'D:/project-support/x-webalbum/store/1/IMG_20220614_213352.jpg', 0, '2022-06-20 11:39:49', 0, 3690938, '图片');
INSERT INTO `my_file` VALUES (385, 'IMG_20220613_010428.jpg', 1, 'D:/project-support/x-webalbum/store/1/IMG_20220613_010428.jpg', 0, '2022-06-20 11:39:49', 0, 10197277, '图片');
INSERT INTO `my_file` VALUES (386, 'mmexport1655269890984.jpg', 1, 'D:/project-support/x-webalbum/store/1/mmexport1655269890984.jpg', 0, '2022-06-20 11:39:49', 0, 242316, '图片');
INSERT INTO `my_file` VALUES (387, 'MVIMG_20220614_000852.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220614_000852.jpg', 0, '2022-06-20 11:39:49', 0, 129799, '图片');
INSERT INTO `my_file` VALUES (388, 'MVIMG_20220613_190023.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220613_190023.jpg', 0, '2022-06-20 11:39:50', 0, 10445133, '图片');
INSERT INTO `my_file` VALUES (389, 'MVIMG_20220613_190024.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220613_190024.jpg', 0, '2022-06-20 11:39:50', 0, 10672807, '图片');
INSERT INTO `my_file` VALUES (390, 'MVIMG_20220614_215608.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220614_215608.jpg', 0, '2022-06-20 11:39:50', 0, 11210121, '图片');
INSERT INTO `my_file` VALUES (391, 'MVIMG_20220614_210012.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220614_210012.jpg', 0, '2022-06-20 11:39:50', 0, 12892561, '图片');
INSERT INTO `my_file` VALUES (392, 'MVIMG_20220614_213449.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220614_213449.jpg', 0, '2022-06-20 11:39:50', 0, 13832302, '图片');
INSERT INTO `my_file` VALUES (393, 'MVIMG_20220614_210024.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220614_210024.jpg', 0, '2022-06-20 11:39:50', 0, 11773841, '图片');
INSERT INTO `my_file` VALUES (394, 'Screenshot_2022-06-12-00-24-55-216_com.tencent.tmgp.sgame.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-12-00-24-55-216_com.tencent.tmgp.sgame.jpg', 0, '2022-06-20 11:39:50', 0, 1192253, '图片');
INSERT INTO `my_file` VALUES (395, 'Screenshot_2022-06-12-02-18-31-438_com.tencent.mm.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-12-02-18-31-438_com.tencent.mm.jpg', 0, '2022-06-20 11:39:50', 0, 1278391, '图片');
INSERT INTO `my_file` VALUES (396, 'Screenshot_2022-06-12-12-21-45-458_com.netease.cloudmusic.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-12-12-21-45-458_com.netease.cloudmusic.jpg', 0, '2022-06-20 11:39:50', 0, 538863, '图片');
INSERT INTO `my_file` VALUES (397, 'Screenshot_2022-06-12-12-27-53-691_com.netease.cloudmusic.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-12-12-27-53-691_com.netease.cloudmusic.jpg', 0, '2022-06-20 11:39:50', 0, 289244, '图片');
INSERT INTO `my_file` VALUES (398, 'Screenshot_2022-06-12-18-11-31-863_com.xunmeng.pinduoduo.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-12-18-11-31-863_com.xunmeng.pinduoduo.jpg', 0, '2022-06-20 11:39:50', 0, 937781, '图片');
INSERT INTO `my_file` VALUES (399, 'Screenshot_2022-06-13-20-50-10-036_com.tencent.mm.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-13-20-50-10-036_com.tencent.mm.jpg', 0, '2022-06-20 11:39:50', 0, 605549, '图片');
INSERT INTO `my_file` VALUES (400, 'Screenshot_2022-06-14-13-43-47-396_com.tencent.mm.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-14-13-43-47-396_com.tencent.mm.jpg', 0, '2022-06-20 11:39:50', 0, 1124417, '图片');
INSERT INTO `my_file` VALUES (401, 'Screenshot_2022-06-14-14-39-01-115_com.tencent.mm.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-14-14-39-01-115_com.tencent.mm.jpg', 0, '2022-06-20 11:39:50', 0, 554232, '图片');
INSERT INTO `my_file` VALUES (402, 'Screenshot_2022-06-14-17-15-41-888_com.tencent.mm.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-14-17-15-41-888_com.tencent.mm.jpg', 0, '2022-06-20 11:39:50', 0, 1406327, '图片');
INSERT INTO `my_file` VALUES (403, 'Screenshot_2022-06-14-17-30-54-059_com.tencent.mm.jpg', 1, 'D:/project-support/x-webalbum/store/1/Screenshot_2022-06-14-17-30-54-059_com.tencent.mm.jpg', 0, '2022-06-20 11:39:50', 0, 613713, '图片');
INSERT INTO `my_file` VALUES (404, 'wx_camera_1655188923271.jpg', 1, 'D:/project-support/x-webalbum/store/1/wx_camera_1655188923271.jpg', 0, '2022-06-20 11:39:50', 0, 957993, '图片');
INSERT INTO `my_file` VALUES (405, 'wx_camera_1655188985467.jpg', 1, 'D:/project-support/x-webalbum/store/1/wx_camera_1655188985467.jpg', 0, '2022-06-20 11:39:50', 0, 828506, '图片');
INSERT INTO `my_file` VALUES (406, 'wx_camera_1655212913300.jpg', 1, 'D:/project-support/x-webalbum/store/1/wx_camera_1655212913300.jpg', 0, '2022-06-20 11:39:50', 0, 1110814, '图片');
INSERT INTO `my_file` VALUES (407, 'wx_camera_1655212921339.jpg', 1, 'D:/project-support/x-webalbum/store/1/wx_camera_1655212921339.jpg', 0, '2022-06-20 11:39:50', 0, 905751, '图片');
INSERT INTO `my_file` VALUES (408, 'MVIMG_20220614_215623.jpg', 1, 'D:/project-support/x-webalbum/store/1/MVIMG_20220614_215623.jpg', 0, '2022-06-20 11:39:50', 0, 13826724, '图片');
INSERT INTO `my_file` VALUES (409, 'IMG_20220613_010405.jpg', 1, 'D:/project-support/x-webalbum/store/1/IMG_20220613_010405.jpg', 0, '2022-06-20 23:03:39', 0, 2475109, '图片');
INSERT INTO `my_file` VALUES (410, '-9d6d72510cf809d.jpg', 1, 'D:/project-support/x-webalbum/store/1/-9d6d72510cf809d.jpg', 0, '2022-06-20 23:18:39', 0, 67037, '图片');
INSERT INTO `my_file` VALUES (411, 'ApiPost6.lnk', 1, 'D:/project-support/x-webalbum/store/1/ApiPost6.lnk', 0, '2022-06-20 23:18:47', 0, 850, '其他');
INSERT INTO `my_file` VALUES (412, 'geek.lnk', 1, 'D:/project-support/x-webalbum/store/1/geek.lnk', 0, '2022-06-20 23:18:55', 0, 972, '其他');
INSERT INTO `my_file` VALUES (413, 'FinalShell.lnk', 1, 'D:/project-support/x-webalbum/store/1/FinalShell.lnk', 0, '2022-06-20 23:19:26', 0, 687, '其他');
INSERT INTO `my_file` VALUES (414, 'JetBrains Toolbox.lnk', 1, 'D:/project-support/x-webalbum/store/1/JetBrains Toolbox.lnk', 0, '2022-06-20 23:19:45', 0, 1501, '其他');
INSERT INTO `my_file` VALUES (415, 'FinalShell_1.lnk', 1, 'D:/project-support/x-webalbum/store/1/FinalShell_1.lnk', 0, '2022-06-20 23:19:52', 0, 687, '其他');
INSERT INTO `my_file` VALUES (416, 'IMG_20220613_010405_1.jpg', 1, 'D:/project-support/x-webalbum/store/1/IMG_20220613_010405_1.jpg', 0, '2022-06-20 23:22:54', 0, 2475109, '图片');
INSERT INTO `my_file` VALUES (419, '屏幕截图 2022-06-16 193112_2.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_2.png', 0, '2022-06-26 16:16:49', 0, 290110, '图片');
INSERT INTO `my_file` VALUES (420, '屏幕截图 2022-06-16 193112_3.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_3.png', 0, '2022-06-26 16:18:07', 0, 290110, '图片');
INSERT INTO `my_file` VALUES (421, '屏幕截图 2022-06-16 193112_4.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_4.png', 0, '2022-06-26 16:20:43', 0, 290110, '图片');
INSERT INTO `my_file` VALUES (422, '屏幕截图 2022-06-16 193112_5.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_5.png', 0, '2022-06-26 16:20:50', 0, 290110, '图片');
INSERT INTO `my_file` VALUES (423, '屏幕截图 2022-06-16 193112_6.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_6.png', 0, '2022-06-26 16:29:17', 0, 290110, '图片');
INSERT INTO `my_file` VALUES (424, '屏幕截图 2022-06-16 193112_7.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_7.png', 0, '2022-06-26 16:29:50', 0, 290110, '图片');
INSERT INTO `my_file` VALUES (425, '屏幕截图 2022-06-16 193112_8.png', 3, 'D:/project-support/x-webalbum/store/3/屏幕截图 2022-06-16 193112_8.png', 0, '2022-06-26 16:30:18', 0, 290110, '图片');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `pass_word` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `user_type` int(11) NOT NULL COMMENT '用户类型 0:超级管理员,1:管理员,2:普通用户',
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像路径',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (-10, 'admin', 'admin', '1234', 0, NULL, NULL, NULL, '2022-07-09 15:23:45');
INSERT INTO `user` VALUES (12, 'admin22', 'admin22', '123', 1, '', 'admin', '', '2022-07-01 22:36:42');
INSERT INTO `user` VALUES (13, 'admin3', 'admin3', '666', 1, '', 'admin', '', '2022-07-02 18:45:43');
INSERT INTO `user` VALUES (14, 'wen 0', 'wen 0', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (15, 'wen 1', 'wen 1', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (16, 'wen 2', 'wen 2', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (17, 'wen 3', 'wen 3', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (18, 'wen 4', 'wen 4', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (19, 'wen 5', 'wen 5', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (20, 'wen 6', 'wen 6', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (21, 'wen 7', 'wen 7', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (22, 'wen 8', 'wen 8', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (23, 'wen 9', 'wen 9', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (24, 'wen 10', 'wen 10', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (25, 'wen 11', 'wen 11', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (26, 'wen 12', 'wen 12', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (27, 'wen 13', 'wen 13', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (28, 'wen 14', 'wen 14', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (29, 'wen 15', 'wen 15', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (30, 'wen 16', 'wen 16', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (31, 'wen 17', 'wen 17', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (32, 'wen 18', 'wen 18', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (33, 'wen 19', 'wen 19', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (34, 'wen 20', 'wen 20', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (35, 'wen 21', 'wen 21', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (36, 'wen 22', 'wen 22', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (37, 'wen 23', 'wen 23', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (38, 'wen 24', 'wen 24', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (39, 'wen 25', 'wen 25', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (40, 'wen 26', 'wen 26', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (41, 'wen 27', 'wen 27', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (42, 'wen 28', 'wen 28', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');
INSERT INTO `user` VALUES (43, 'wen 29', 'wen 29', '123', 2, '666', '@qq', '/#', '2022-07-05 22:35:52');

SET FOREIGN_KEY_CHECKS = 1;
