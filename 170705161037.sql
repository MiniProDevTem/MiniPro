/*
MySQL Backup
Source Server Version: 5.0.22
Source Database: minipro
Date: 2017/7/5 16:10:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `game_inform`
-- ----------------------------
DROP TABLE IF EXISTS `game_inform`;
CREATE TABLE `game_inform` (
  `uuid` varchar(100) NOT NULL,
  `hero_id` int(11) NOT NULL default '0',
  `time_use` int(11) default NULL,
  `win_rate` double default NULL,
  PRIMARY KEY  (`uuid`,`hero_id`),
  KEY `hero_id` (`hero_id`),
  CONSTRAINT `game_inform_ibfk_1` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `hero`
-- ----------------------------
DROP TABLE IF EXISTS `hero`;
CREATE TABLE `hero` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `location` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `open_id` varchar(100) default NULL,
  `uuid` varchar(100) NOT NULL,
  `sex` varchar(20) default NULL,
  `age` int(11) default NULL,
  `name` varchar(100) default NULL,
  `place` varchar(200) default NULL,
  `birthday` varchar(200) default NULL,
  `location` varchar(200) default NULL,
  `qq` varchar(50) default NULL,
  `head_url` varchar(200) default NULL,
  `voice_url` varchar(200) default NULL,
  `star` varchar(200) default NULL,
  `matching` varchar(200) default NULL,
  `trank_rate` double default NULL,
  `warrior_rate` double default NULL,
  `assassin_rate` double default NULL,
  `master_rate` double default NULL,
  `auxiliary_rate` double default NULL,
  `shooter_rate` double default NULL,
  `images` text,
  `levl` varchar(200) default NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `game_inform` VALUES ('0002','7','20','0.3'), ('0003','7','20','0.3'), ('0004','1','20','0.3'), ('0004','2','10','0.2'), ('0004','3','3','1'), ('0004','4','2','1'), ('0004','7','20','0.3'), ('0004','10','45','0'), ('0005','1','20','0.4'), ('0005','2','20','0.4'), ('0005','3','20','0.4'), ('0005','4','20','0.4'), ('0005','5','20','0.4'), ('0005','6','20','0.4'), ('0005','7','20','0.4'), ('0005','8','20','0.4'), ('0005','9','20','0.4'), ('0005','10','45','0'), ('0006','1','20','0.4'), ('0006','2','20','0.3'), ('0006','3','20','0.3'), ('0006','4','20','0.3'), ('0006','5','20','0.3'), ('0006','6','20','0.3'), ('0006','7','20','0.3'), ('0006','8','20','0.3'), ('0006','9','20','0.3'), ('0006','10','45','0'), ('0007','1','20','0.3'), ('0007','2','20','0.3'), ('0007','3','20','0.3'), ('0007','4','20','0.3'), ('0007','5','20','0.3'), ('0007','6','20','0.3'), ('0007','7','20','0.3'), ('0007','8','20','0.3'), ('0007','9','20','0.3'), ('0007','10','45','0'), ('0008','1','20','0.3'), ('0008','2','20','0.3'), ('0008','3','20','0.3'), ('0008','4','20','0.3'), ('0008','5','20','0.3'), ('0008','6','20','0.3'), ('0008','7','20','0.3'), ('0008','8','20','0.3'), ('0008','9','20','0.3'), ('0009','1','20','0.3'), ('0009','2','20','0.3'), ('0009','3','20','0.3'), ('0009','4','20','0.3'), ('0009','5','20','0.3'), ('0009','6','20','0.3'), ('0009','7','20','0.3'), ('0009','8','20','0.3'), ('0009','9','20','0.3');
INSERT INTO `hero` VALUES ('1','aaaa','1'), ('2','bbb','1'), ('3','ccc','1'), ('4','ddd','2'), ('5','eee','2'), ('6','fff','2'), ('7','ggg','3'), ('8','hhh','3'), ('9','iii','3'), ('10','jjj','3'), ('13','mmm','3');
INSERT INTO `user` VALUES ('0001','0001','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0002','0002','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0003','0003','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0004','0004','325','3245','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0','{\"images\":[{\"url\":\"456456\",\"timestamp\":1499241292318},{\"url\":\"45645654\",\"timestamp\":1499241298863}]}',NULL), ('0005','0005','325','3245','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0.266666666666667','0.266666666666667','0.466666666666667','0','0','0',NULL,NULL), ('0006','0006','325','3245','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0.266666666666667','0.266666666666667','0.466666666666667','0','0','0',NULL,NULL), ('0007','0007','325','3245','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0.267','0.267','0.467','0','0','0',NULL,NULL), ('0008','0008','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0009','0009','325','3245','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0','{\"images\":[{\"url\":\"23424234\",\"timestamp\":1499173092725}]}',NULL), ('0010','0010','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0011','0011','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0012','0012','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0013','0013','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0014','0014','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0015','0015','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0016','0016','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0017','0017','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0018','0018','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0019','0019','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0020','0020','325','0','3532','3245','325','352','345',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL), ('0025','f38ca216-5b89-4bbc-82e6-a4cf57b83f9d','sdf','0','dgd','sfd','sf','sdf','sdf',NULL,NULL,NULL,NULL,'0','0','0','0','0','0',NULL,NULL);
