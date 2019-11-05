CREATE TABLE `app_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(6) DEFAULT NULL COMMENT '密码盐值',
  `master` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否主账号',
  `activated` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否激活',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_pwd` bit(1) DEFAULT b'0' COMMENT '是否修改过密码 0未修改 1:已修改',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4;



CREATE DEFINER=`root`@`%` PROCEDURE `insertData`()
BEGIN

DECLARE i INT DEFAULT 1;

while i<1000000
do
INSERT INTO `zerer`.`app_account` (`mobile`, `password`, `salt`, `master`, `activated`, `create_time`, `update_time`, `update_pwd`) VALUES ( i, '123456', '111', b'1', b'1', '2019-11-05 16:17:33', '2019-11-05 16:17:37', b'0');
set i = i+1;
end while;
commit;
end


