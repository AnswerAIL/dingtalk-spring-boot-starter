# Dinger(叮鸽) ![GitHub license](https://img.shields.io/github/license/AnswerAIL/dingtalk-spring-boot-starter)
[![Dinger Logo](https://gitee.com/jaemon/docs/raw/master/dinger.png)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)


[![Maven Central](https://img.shields.io/maven-central/v/com.github.answerail/dinger-spring-boot-starter)](https://mvnrepository.com/artifact/com.github.answerail/dinger-spring-boot-starter)
[![GitHub stars](https://img.shields.io/github/stars/AnswerAIL/dingtalk-spring-boot-starter.svg?style=social)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)
[![Gitee stars](https://gitee.com/jaemon/dingtalk-spring-boot-starter/badge/star.svg?theme=dark)](https://gitee.com/jaemon/dingtalk-spring-boot-starter)
![JDK](https://img.shields.io/badge/JDK-1.8+-green?logo=appveyor)
![SpringBoot](https://img.shields.io/badge/springboot-1.x%20&%202.x-green?logo=appveyor)


&nbsp;


## What(Dinger是什么)
Dinger是一个以SpringBoot框架为基础开发的消息发送中间件， 对如下移动办公系统的群机器人API做了一层封装，让使用更简单便捷。
- [钉钉](https://open.dingtalk.com/document/group/custom-robot-access)
- [企业微信](https://developer.work.weixin.qq.com/document/path/91770)
- [飞书](https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN#756b882f)

只需要简单的配置（最简单的发送功能只需要一行代码），即可快速的在springboot项目中将消息发送到指定的钉钉或企业微信群聊中。

- [Dinger在线文档](https://answerail.gitee.io/docsify-jaemon)

- [Dinger QQ交流群： 1002507383](https://jq.qq.com/?_wv=1027&k=xbcwxp0i)

&nbsp;

***

## Why(为什么用Dinger)
 - [x] 配置简单，上手容易，无需花费太多精力在群机器人API的使用上；
 - [x] 插拔式功能组件，和业务代码解耦；
 - [x] 核心功能面向接口编程, 可以据具体业务对功能进行定制化来满足不同的业务需求；
 - [x] 支持集中式管理消息，提供xml标签，支持编写动态消息体；
 - [x] 基于具体消息编程，消息体可支持XML标签方式配置和注解方式定义；
 - [x] 支持钉钉、企业微信、飞书群机器人一键切换使用和混合使用；

&nbsp;

### 支持Dinger
> ★ **欢迎『[Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)』 或 『[Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter)』点下Star让更多码友知道Dinger的存在**

> ❤ [Gitee捐赠](https://gitee.com/jaemon/dingtalk-spring-boot-starter): 如果觉得Dinger不错, 条件允许的话捐赠杯奶茶犒劳下维护者, 感谢您的支持和鼓励^_^。

&nbsp;

***

## How(如何使用Dinger-快速使用)
### 一、引入依赖
```xml
<dependency>
    <groupId>com.github.answerail</groupId>
    <artifactId>dinger-spring-boot-starter</artifactId>
    <version>${dinger.version}</version>
</dependency>
```
> **dinger.version版本号取值** ☞ [Github](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/wiki/Dinger-1.1-Upgrade-Log) 或 [Gitee](https://gitee.com/jaemon/dingtalk-spring-boot-starter/wikis/Dinger-1.1-Upgrade-Log?sort_id=3312594)

&nbsp;

### 二、application.yml 配置
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

**使用企业微信群机器人配置**
```yaml
spring:
  dinger:
    project-id: ${spring.application.name}
    dingers:
      # 使用企业微信机器人, 请根据自己机器人配置信息进行修改
      wetalk:
        token-id: 32865206-7082-46l5-8j39-2m7ycy6d868
```

**使用飞书群机器人配置**
```yaml
spring:
  dinger:
    project-id: ${spring.application.name}
    dingers:
      # 使用飞书机器人, 请根据自己机器人配置信息进行修改
      bytetalk:
        token-id: 20200528-0824-20jm-21hy-5yc556210y15
```

&nbsp;

### 三、代码中使用
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
- [Dinger在线文档](https://answerail.gitee.io/docsify-jaemon)

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
