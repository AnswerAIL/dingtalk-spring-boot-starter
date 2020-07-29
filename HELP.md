# 生成GPG秘钥
 - windows版下载地址： [https://www.gpg4win.org/download.html](https://www.gpg4win.org/download.html)
 - 其他下载： [gnupg](https://www.gnupg.org/download/)
```bash
    # 检查是否安装成功
    gpg --version 
    
    # 生成密钥对(设置对应的用户名[Answer.AI.L]、邮箱[answer_ljm@163.com]、密码[passphrase, 下面会用到])
    gpg --gen-key 
    
    # 查看公钥列表
    gpg --list-keys 
    -------------------------------------------------
    pub   rsa2048 2020-07-22 [SC] [expires: 2023-12-31]
          21D5FBF1E95FA504C4B652E56F65CBF6BDAA1CDQ
    uid           [ultimate] Answer.AI.L <answer_ljm@163.com>
    sub   rsa2048 2020-07-22 [E] [expires: 2022-07-22]
    
    # 将公钥发布到 PGP 密钥服务器,公钥ID(后16位即可), 两个密钥服务器都要发布
    gpg --keyserver hkp://pool.sks-keyservers.net --send-keys 6F65CBF6BDAA1CDQ
    gpg --keyserver hkp://keyserver.ubuntu.com:11371 --send-keys 6F65CBF6BDAA1CDQ
    
    # 查询公钥是否发布成功,公钥ID(后16位即可)
    gpg --keyserver hkp://pool.sks-keyservers.net --recv-keys  6F65CBF6BDAA1CDQ
    gpg --keyserver hkp://keyserver.ubuntu.com:11371 --recv-keys 6F65CBF6BDAA1CDQ
```

&nbsp;

# 发布
> mvn clean deploy
>
> mvn clean deploy -Dgpg.passphrase=******
>
> mvn clean deploy -P release -Dgpg.passphrase=******
>
> mvn clean deploy -P release -Darguments="gpg.passphrase=******"

&nbsp;

# **settings.xml 配置**
```xml
    <server>
        <id>ossrh</id>
        <username>Answer.AI.L</username>
        <password>Sonatype密码</password>
    </server>
```

&nbsp;

# 常用地址
 - [注册 Sonatype 的账户](https://issues.sonatype.org/secure/Signup!default.jspa)
 - [创建issue-Create](https://issues.sonatype.org/secure/Dashboard.jspa)
    - Project: Community Support - Open Source Project Repository Hosting (OSSRH)
    - Issue Type： New Project
    - Group Id: com.github.answerail
    - Project URL: https://github.com/AnswerAIL/dingtalk-spring-boot-starter
    - SCM url: https://github.com/AnswerAIL/dingtalk-spring-boot-starter.git
    - Already Synced to Central: No

 - [搜索地址： https://search.maven.org/](https://search.maven.org/)
 - [中央仓库地址： http://mvnrepository.com/](http://mvnrepository.com/)
 - [我发布的构件地址： http://mvnrepository.com/artifact/com.github.XXX](http://mvnrepository.com/artifact/com.github.answerail)
 - [dingtalk issue OSSRH-59361](https://issues.sonatype.org/browse/OSSRH-59361)
 - [sonatype 仓库](https://oss.sonatype.org/#stagingRepositories)
   - Staging Repositories 中选择发布的临时包点 Close， 如果校验通过, 再选中该包， 点击 Release 按钮， 即发布到Release版本中
 
&nbsp;

## References
 - [提交本地jar到Maven中央仓库(Windows)的那些小事](https://blog.csdn.net/u010651369/article/details/79970726)
 - [Maven(6) Java上传本地jar包到maven中央仓库](https://blog.csdn.net/qq_38225558/article/details/94381467)
 - [把自己的项目发布到maven中心仓库----论姿势的正确性](https://blog.csdn.net/qq_28802119/article/details/85256852)
 - [发布jar包到Maven中央仓库](https://blog.csdn.net/dawei0523/article/details/84918820)
 - [钉钉开放平台开发手册](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h)
 - [Java上传本地jar包到maven中央仓库的坑逼经历](https://blog.csdn.net/pdsu161530247/article/details/105429597)
 - [Spring Factories](https://blog.csdn.net/u010979642/article/details/107642130)