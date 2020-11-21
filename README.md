# dingtalk-spring-boot-starter
# dingtalk-spring-boot-starter ![GitHub license](https://img.shields.io/github/license/AnswerAIL/dingtalk-spring-boot-starter)
![Maven Central](https://img.shields.io/maven-central/v/com.github.answerail/dingtalk-spring-boot-starter)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/AnswerAIL/dingtalk-spring-boot-starter)
![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/AnswerAIL/dingtalk-spring-boot-starter)


[![GitHub stars](https://img.shields.io/github/stars/AnswerAIL/dingtalk-spring-boot-starter.svg?style=social)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter)
![GitHub all releases](https://img.shields.io/github/downloads/AnswerAIL/dingtalk-spring-boot-starter/total?style=social)
[![Gitee stars](https://gitee.com/jaemon/dingtalk-spring-boot-starter/badge/star.svg?theme=dark)](https://gitee.com/jaemon/dingtalk-spring-boot-starter)


[![GitHub issues](https://img.shields.io/github/issues/AnswerAIL/dingtalk-spring-boot-starter)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/issues?q=is%3Aopen+is%3Aissue)
[![GitHub closed issues](https://img.shields.io/github/issues-closed/AnswerAIL/dingtalk-spring-boot-starter)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/issues?q=is%3Aissue+is%3Aclosed)


[![Read the Docs](https://img.shields.io/readthedocs/pip)](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/wiki)


## What
springboot集成钉钉机器人实现消息通知中间件。项目基于[钉钉开放平台-群机器人](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5) API的基础上做了一层封装，让使用更简单便捷。

只需要简单的配置（最简单的发送功能只需要一行代码），即可快速的在springboot项目中将消息发送到指定的钉钉群聊中。

&nbsp;


## Why
 - **`上手简单`**： 配置简单，无需花费太多精力在群机器人的使用上；
 - **`代码解耦`**： 插拔式功能组件，和业务代码解耦；
 - **`扩展性强`**： 核心功能面向接口编程, 可以据具体业务对功能进行定制化；
 - **`用法多样`**： 消息体支持[V1.X](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/Getting%20Started%20V1.0.md)的完全自定义和[V2.X](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/Getting%20Started%20V2.0.md)的XML方式配置及注解方式定义；


&nbsp;

## How
- [V1.X快速入门](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/Getting%20Started%20V1.0.md)

- [V2.X快速入门](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/Getting%20Started%20V2.0.md)


&nbsp;

## 版本变更说明
| 版本号        | 发布日期       | 变更说明                                   | 备注 |
| ------------- | ---------- | ------------------------------------------ | ---------- |
| [V1.0.1](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.1-RELEASE) | 2020-07-23 | 初始化版本<br /> + 支持通知消息体自定义<br />+ 支持异常回调 | [V1.X使用文档](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/V1.0.md) |
| [V1.0.2](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.2-RELEASE) | 2020-07-24 | + 支持markdown消息体 | - |
| [V1.0.3](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.3-RELEASE) | 2020-07-25 | + 支持验签<br /> + 支持异步消息发送<br /> + 支持异步结果回调 | - |
| [V1.0.4](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.4-RELEASE) | 2020-08-01 | + 新增支持以下消息类型<br /> (1). 独立跳转ActionCard类型<br />(2). 整体跳转ActionCard类型<br />(3). FeedCard类型<br /> + 支持服务状态监控消息通知<br /> + 支持自定义服务状态通知消息体<br /> + 支持DingTalk全局开关配置<br /> + 支持个性化统一配置 | - |
| [V1.0.5](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.5-RELEASE) | 2020-08-08 | + 支持tokenId加密<br /> + 支持配置属性校验<br /> | - |
| [V2.0.0](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.0-RELEASE) | 2020-09-06 | + 支持 XXXDinger.xml xml方式消息配置<br /> + 支持@DingerText&@DingerMarkdown注解方式消息配置 | [V2.X使用文档](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/V2.0.md) |
| [V2.0.1](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.1-RELEASE) | 2020-09-13 | + 新增 @DingerConfiguration 注解支持Dinger层级别钉钉机器人信息配置<br /> + 新增 @AsyncExecute 注解支持Dinger层级别设置异步发送 | - |
| [V2.0.2](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.2-RELEASE) | 2020-09-20 | + 新增dinger xml文件编写约束<br /> M 修复部分存在缺陷  | - |
| [V2.0.3](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.3-RELEASE) | 2020-10-14 | M fixed #2  | - |
| [V2.0.4](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.4-RELEASE) | 2020-10-18 | M fixed #3 && 部分存在缺陷  | - |
| [V2.0.5](https://github.com/AnswerAIL/dingtalk-spring-boot-starter) | 2020-11-11 | + 支持动态配置机器人信息  | - |

&nbsp;

## 问题反馈
***
> 有问题欢迎提Issue
>
> [github issues](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/issues)
>
> [gitee issues](https://gitee.com/jaemon/dingtalk-spring-boot-starter/issues)
***

&nbsp;