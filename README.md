# dingtalk-spring-boot-starter


springboot集成钉钉机器人实现消息通知中间件。项目基于[钉钉开放平台-群机器人](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5) API的基础上做了一层封装，让使用更简单便捷。

&nbsp;

## 版本变更说明
| 版本号        | 发布日期       | 变更说明                                   | 备注 |
| ------------- | ---------- | ------------------------------------------ | ---------- |
| [1.0.1-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.1-RELEASE) | 2020-07-23 | 初始化版本<br /> + 支持通知消息体自定义<br />+ 支持异常回调 | [V1.0.X使用文档](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/V1.0.md) |
| [1.0.2-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.2-RELEASE) | 2020-07-24 | + 支持markdown消息体 | - |
| [1.0.3-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.3-RELEASE) | 2020-07-25 | + 支持验签<br /> + 支持异步消息发送<br /> + 支持异步结果回调 | - |
| [1.0.4-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.4-RELEASE) | 2020-08-01 | + 新增支持以下消息类型<br /> (1). 独立跳转ActionCard类型<br />(2). 整体跳转ActionCard类型<br />(3). FeedCard类型<br /> + 支持服务状态监控消息通知<br /> + 支持自定义服务状态通知消息体<br /> + 支持DingTalk全局开关配置<br /> + 支持个性化统一配置 | - |
| [1.0.5-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/1.0.5-RELEASE) | 2020-08-08 | + 支持tokenId加密<br /> + 支持配置属性校验<br /> | - |
| [2.0.0-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.0-RELEASE) | 2020-09-06 | + 支持 XXXDinger.xml xml方式消息配置<br /> + 支持@DingerText&@DingerMarkdown注解方式消息配置 | [V2.0.X使用文档](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/V2.0.md) |
| [2.0.1-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.1-RELEASE) | 2020-09-13 | + 新增 @DingerConfiguration 注解支持Dinger层级别钉钉机器人信息配置<br /> + 新增 @AsyncExecute 注解支持Dinger层级别设置异步发送 | - |
| [2.0.2-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.2-RELEASE) | 2020-09-20 | + 新增dinger xml文件编写约束<br /> M 修复部分存在缺陷  | - |
| [2.0.3-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/releases/tag/2.0.3-RELEASE) | 2020-10-14 | M fixed #2  | - |
| [2.0.4-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter) | 2020-10-18 | M fixed #3 && 部分存在缺陷  | - |

&nbsp;


## 快速入门文档
- [V1.0快速入门](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/Getting%20Started%20V1.0.md)

- [V2.0快速入门](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/master/docs/Getting%20Started%20V2.0.md)


&nbsp;

***
> 有问题欢迎提Issue
>
> [github issues](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/issues)
>
> [gitee issues](https://gitee.com/jaemon/dingtalk-spring-boot-starter/issues)
***

&nbsp;