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