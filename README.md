## 社区

##资料
[Spring文档](https://spring.io/guides)
[Spring Web文档](https://spring.io/guides/gs/serving-web-content/)
[github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[bootstrap](https://v3.bootcss.com/getting-started/)
[github oauth授权文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[菜鸟编程mysql](https://www.runoob.com/mysql/mysql-tutorial.html)

##工具
[git下载](https://git-scm.com/downloads)

##脚本
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `accountId` varchar(100) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmtCreate` bigint(20) DEFAULT NULL,
  `gmtModified` bigint(20) DEFAULT NULL,
  `bio` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

```
```sql
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `gmtCreate` bigint(20) DEFAULT NULL,
  `gmtModified` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `commentCount` int(11) DEFAULT '0',
  `viewCount` int(11) DEFAULT '0',
  `likeCount` int(11) DEFAULT '0',
  `tag` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8
```
```sql
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL COMMENT '问题的id',
  `type` int(11) NOT NULL COMMENT '父类类型，一级二级回复枚举',
  `commentator` int(11) NOT NULL COMMENT '评论人id',
  `gmt_create` bigint(20) NOT NULL COMMENT '评论时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '更新时间',
  `like_count` bigint(20) DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

```bash
 mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```