# Dinger(叮鸽) ![GitHub license](https://img.shields.io/github/license/AnswerAIL/dingtalk-spring-boot-starter)
[![Dinger Logo](https://gitee.com/jaemon/docs/raw/master/dinger.png)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)


[![Maven Central](https://img.shields.io/maven-central/v/com.github.answerail/dinger-spring-boot-starter)](https://mvnrepository.com/artifact/com.github.answerail/dinger-spring-boot-starter)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/AnswerAIL/dingtalk-spring-boot-starter)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases)
[![GitHub stars](https://img.shields.io/github/stars/AnswerAIL/dingtalk-spring-boot-starter.svg?style=social)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)
[![Gitee stars](https://gitee.com/jaemon/dingtalk-spring-boot-starter/badge/star.svg?theme=dark)](https://gitee.com/jaemon/dingtalk-spring-boot-starter)
![JDK](https://img.shields.io/badge/JDK-1.8+-green?logo=appveyor)
![SpringBoot](https://img.shields.io/badge/springboot-1.x%20&%202.x-green?logo=appveyor)


&nbsp;


## What(Dinger是什么)
Dinger是一个以SpringBoot框架为基础开发的消息发送中间件， 对现有两大移动办公系统[钉钉](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5)和[企业微信](https://work.weixin.qq.com/api/doc/90000/90136/91770)的群机器人API做了一层封装，让使用更简单便捷。

只需要简单的配置（最简单的发送功能只需要一行代码），即可快速的在springboot项目中将消息发送到指定的钉钉或企业微信群聊中。


&nbsp;


## Why(为什么用Dinger)
 - [x] 配置简单，上手容易，无需花费太多精力在群机器人API的使用上；
 - [x] 插拔式功能组件，和业务代码解耦；
 - [x] 核心功能面向接口编程, 可以据具体业务对功能进行定制化来满足不同的业务需求；
 - [x] 支持集中式管理消息，提供xml标签，支持编写动态消息体；
 - [x] 基于具体消息编程，消息体可支持XML标签方式配置和注解方式定义；
 - [x] 支持钉钉和企业微信群机器人一键切换使用和混合使用；

&nbsp;

> ★ **如果觉得项目对您的工作有帮助的话, 欢迎『[Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)』 或 『[Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter)』加星关注☺**

&nbsp;


## How(如何使用Dinger-快速使用)
### 引入依赖
```xml
<dependency>
    <groupId>com.github.answerail</groupId>
    <artifactId>dinger-spring-boot-starter</artifactId>
    <version>${dinger.version}</version>
</dependency>
```
> **dinger.version版本号取值** ☞ [Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/wiki/Dinger-1.1-Upgrade-Log) 或 [Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter/wikis/Dinger-1.1-Upgrade-Log?sort_id=3312594)

### 配置文件配置
**使用钉钉群机器人配置**
```yaml
spring:
  dinger:
    project-id: ${spring.application.name}
    dingers:
      # 使用钉钉机器人, 请根据自己机器人配置信息进行修改
      dingtalk:
        tokenId: 87dbeb7bc28894c3ycyl3d12457228ad309966275b5f427cd85f9025ebb520cf
        secret: AEQ74a9039ai01f2ljm017b90ycye9asg6335f97c658ff37ff371ec8120581c7f09
```

**使用企业群机器人配置**
```yaml
spring:
  dinger:
    project-id: ${spring.application.name}
    dingers:
      # 使用企业微信机器人, 请根据自己机器人配置信息进行修改
      wetalk:
        token-id: 32865206-7082-46l5-8j39-2m7ycy6d868
```

### 代码中使用
```java
@Component
public class AppInit implements InitializingBean {
    @Autowired
    private DingerSender dingerSender;
    @Override
    public void afterPropertiesSet() throws Exception {
        // 发送text类型消息
        dingerSender.send(
                MessageSubType.TEXT,
                DingerRequest.request("Hello World, Hello Dinger")
        );

        // 发送markdown类型消息
        dingerSender.send(
                MessageSubType.MARKDOWN,
                DingerRequest.request("Hello World, Hello Dinger", "启动通知")
        );
    }
}
```
更多功能请移步 『[Github Dinger wiki](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/wiki)』 或 『[Gitee Dinger wiki](https://gitee.com/jaemon/dingtalk-spring-boot-starter/wikis)』


&nbsp;

## Documentation, Getting Started and Developer Guides
- [Dinger Wiki-Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/wiki)

- [Dinger Wiki-Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter/wikis)


&nbsp;


## Upgrade Log
- [版本变更日志-Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/wiki/Dinger-1.1-Upgrade-Log)

- [版本变更日志-Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter/wikis/Dinger-1.1-Upgrade-Log)


&nbsp;


## Feedback
✍ **有任何建议或问题欢迎提Issue~**

- [Issues-Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/issues)

- [Issues-Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter/issues)
***

&nbsp;
