SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


/**
字段名字首字母小写
字段要设置默认值，字符串类型的默认值为 空字符串
字段要有 comment 备注
表要有 comment 备注
*/

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userCode` varchar(64) DEFAULT '' comment '账号',
  `userName` varchar(64) DEFAULT '' comment '显示名称' ,
  `password` varchar(64) DEFAULT '' comment '密码',
  `status` int(11) DEFAULT 1 comment '0-记录已删除；1-未删除',
  `createUser` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyUser` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user`(`id`, `userCode`, `userName`, `password`, `status`, `createUser`, `createTime`, `modifyUser`, `modifyTime`) VALUES (1, 'admin', '管理页', '123', 1, 'admin', '2021-03-02 23:22:11', NULL, NULL);

create table role(
  id int(11) not null auto_increment,
  rolename varchar(64) default '' comment '角色名称',
  `status` int(11) DEFAULT 1 comment '0-记录已删除；1-未删除',
  `createUser` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyUser` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
)

create table userRole(
  id int(11) not null auto_increment,
  userId int(11) default 0 comment '用户id',
  roleId int(11) default 0 comment '角色id',
  `status` int(11) DEFAULT 1 comment '0-记录已删除；1-未删除',
  `createUser` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyUser` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)

create table resource(
  id int(11) not null auto_increment,
  name varchar(64) default '' comment '名称',
  category int(11) default 100 comment '资源类型：100-导航资源；101-按钮资源',
  requestUrl varchar(20) default '' comment '导航url',
  `status` int(11) DEFAULT 1 comment '0-记录已删除；1-未删除',
  `createUser` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyUser` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)

create table resourceRole(
  id int(11) not null auto_increment,
  resourceId int(11) default 0 comment '资源id',
  roleId int(11) default 0 comment '角色id',
  `status` int(11) DEFAULT 1 comment '0-记录已删除；1-未删除',
  `createUser` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyUser` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)

create table resourceUser(
  id int(11) not null auto_increment,
  resourceId int(11) default 0 comment '资源id',
  userId int(11) default 0 comment '用户id',
  `status` int(11) DEFAULT 1 comment '0-记录已删除；1-未删除',
  `createUser` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `modifyUser` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)