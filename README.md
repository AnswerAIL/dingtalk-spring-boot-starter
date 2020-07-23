# dingtalk-spring-boot-starter

集成钉钉机器人实现消息通知

&nbsp;

## 版本变更说明
| 版本号        | 日期       | 变更说明                                   |
| ------------- | ---------- | ------------------------------------------ |
| [1.0.1-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/1.0.1) | 2020-07-23 | 初始化版本<br /> + 支持通知消息体自定义<br />+ 支持异常回调 |
| 1.0.2-RELEASE | 2020-07-24 | + 支持markdown消息体 |



&nbsp;

## 使用说明
### 引入maven依赖
```xml
    <!-- 方式1： 本地jar包 -->
    <dependency>
        <groupId>com.github.answerail</groupId>
        <artifactId>dingtalk-spring-boot-starter</artifactId>
        <version>1.0.2-RELEASE</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/dingtalk-spring-boot-starter-1.0.2-RELEASE.jar</systemPath>
    </dependency>
    

    <!-- 方式2： 拉取maven仓库获取jar -->
    <dependency>
        <groupId>com.github.answerail</groupId>
        <artifactId>dingtalk-spring-boot-starter</artifactId>
        <version>${answerail-dingtalk.version}</version>
    </dependency>
```
**answerail-dingtalk.version目前支持版本**
 - 1.0.1-RELEASE
 - 1.0.2-RELEASE

***
&nbsp;

### 配置
```yaml
spring:
  dingtalk:
    project-id: oms
    token-id: c60d4824e0ba4a30544e81212256789331d68b0085ed1a5b2279715741355fbc
    title: 消息推送
```
 - token-id： 必填， [获取方式](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/26eaddd5)
 - project-id： 项目名称， 必填
 - title： 选填。 默认值(通知)

&nbsp;

### SpringBoot中使用
```java
    public class Demo {
        @Autowired
        private DingTalkRobot dingTalkRobot;
        
        public void test() {
            // text类型
            dingTalkRobot.send(MsgTypeEnum.TEXT, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。");
            // text类型带@
            dingTalkRobot.send(MsgTypeEnum.TEXT, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。", Lists.newArrayList("135XXXXXXXX"));
            // text类型带全部
            dingTalkRobot.send(MsgTypeEnum.TEXT, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。");

            // markdown类型
            dingTalkRobot.send(MsgTypeEnum.MARKDOWN, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。");
            // markdown类型带@
            dingTalkRobot.send(MsgTypeEnum.MARKDOWN, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。", Lists.newArrayList("135XXXXXXXX"));
        }         
    }
```
> markdown消息体暂时不支持@全部

&nbsp;

### 自定义消息体
```java
    @Configuration
    public class MyConfiguration {
        // 自定义text类型消息体
        @Bean
        public CustomMessage textMessage() {
            return (dingTalkProperties, subTitle, keyword, content, phones) -> {
                String message = null;
                // ...
                return message;
            };
        }
    
        // 自定义markdown类型消息体
        @Bean
        public CustomMessage markDownMessage() {
            return (dingTalkProperties, subTitle, keyword, content, phones) -> {
                String message = null;
                // ...
                return message;
            };
        }
    }
```

&nbsp;

### 自定义异常回调
```java
    @Configuration
    public class MyConfiguration {
        // 自定义异常回调函数
        @Bean
        public Notice notice() {
            return (dingTalkProperties, keyword, message, ex) -> {
                // ...
            };
        }
    }
```