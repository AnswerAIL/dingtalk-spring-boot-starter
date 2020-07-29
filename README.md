# dingtalk-spring-boot-starter

[toc]

集成钉钉机器人实现消息通知

&nbsp;

## 版本变更说明
| 版本号        | 日期       | 变更说明                                   |
| ------------- | ---------- | ------------------------------------------ |
| [1.0.1-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/1.0.1) | 2020-07-23 | 初始化版本<br /> + 支持通知消息体自定义<br />+ 支持异常回调 |
| [1.0.2-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/1.0.2) | 2020-07-24 | + 支持markdown消息体 |
| [1.0.3-RELEASE](https://github.com/AnswerAIL/dingtalk-spring-boot-starter/tree/1.0.3) | 2020-07-25 | + 支持验签<br /> + 支持异步处理<br /> + 支持异步回调函数 |
| 1.0.4-RELEASE | 2020-XX-XX | + 新增支持以下消息类型<br /> (1). 独立跳转ActionCard类型<br />(2). 整体跳转ActionCard类型<br />(3). FeedCard类型 |


&nbsp;

## 使用说明
### 引入maven依赖
```xml
    <!-- 方式1： 本地jar包 -->
    <dependency>
        <groupId>com.github.answerail</groupId>
        <artifactId>dingtalk-spring-boot-starter</artifactId>
        <version>1.0.3-RELEASE</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/dingtalk-spring-boot-starter-1.0.3-RELEASE.jar</systemPath>
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
 - 1.0.3-RELEASE
 - 1.0.4-RELEASE

***
&nbsp;

### 配置
```yaml
spring:
  dingtalk:
    # 仅支持text和markdown消息类型
    project-id: oms
    token-id: c60d4824e0ba4a30544e81212256789331d68b0085ed1a5b2279715741355fbc
    # 自定义关键字, 仅支持text和markdown消息类型
    title: 消息推送
    # 签名秘钥
    secret: APC3eb471b2761851d6ddd1abcndf2d97be21447d8818f1231c5ed61234as52d1w0
    # 是否异步执行
    async: true
    # 异步处理线程池参数配置, 可选
#   executor-pool:
    # http客户端配置(优先使用项目中已有的), 可选
#   ok-http:
```
 - token-id： 必填， [获取方式](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/26eaddd5)
 - project-id： 项目名称， 必填
 - title： 选填。 默认值(通知)
 - secret： 选填， 需要签名时必填， [获取方式](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/26eaddd5)
 - async： 选填， true | false

&nbsp;

### SpringBoot中使用
#### 预警消息推送
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
            dingTalkRobot.sendAll(MsgTypeEnum.TEXT, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。");

            // markdown类型
            dingTalkRobot.send(MsgTypeEnum.MARKDOWN, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。");
            // markdown类型带@
            dingTalkRobot.send(MsgTypeEnum.MARKDOWN, "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动通知", "服务启动异常啦。。。", Lists.newArrayList("135XXXXXXXX"));
        }         
    }
```
> markdown消息体暂时不支持@全部

&nbsp;

#### 其他格式消息类型
```java
    public class Demo {
        @Autowired
        private DingTalkRobot dingTalkRobot;

        // 整体跳转ActionCard类型
        public void singleActionCardReq() {
            String keyword = "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM";
    
            SingleActionCardReq actionCardReq = new SingleActionCardReq();
            SingleActionCardReq.SingleActionCard actionCard = new SingleActionCardReq.SingleActionCard();
            actionCardReq.setActionCard(actionCard);
            actionCard.setTitle("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身");
            actionCard.setText("\"![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) \n" +
                    " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
                    " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划");
            actionCard.setBtnOrientation("0");
            actionCard.setSingleTitle("阅读全文");
            actionCard.setSingleURL("https://github.com/AnswerAIL");
    
            dingTalkRobot.send(keyword, actionCardReq);
        }

        // 独立跳转ActionCard类型
        public void actionCardReq() {
            String keyword = "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM";
    
            ActionCardReq actionCardReq = new ActionCardReq();
            ActionCardReq.ActionCard actionCard = new ActionCardReq.ActionCard();
            actionCardReq.setActionCard(actionCard);
            actionCard.setTitle("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身");
            actionCard.setText("![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) \n" +
                    " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
                    " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划");
            actionCard.setBtnOrientation("0");
            List<ActionCardReq.ActionCard.Button> buttons = Lists.newArrayList();
            actionCard.setBtns(buttons);
            buttons.add(new ActionCardReq.ActionCard.Button("内容不错", "https://github.com/AnswerAIL"));
            buttons.add(new ActionCardReq.ActionCard.Button("不感兴趣", "https://github.com/AnswerAIL"));
    
            dingTalkRobot.send(keyword, actionCardReq);
        }
    
        // FeedCard类型
        public void feedCardReq() {
            String keyword = "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM";
    
            FeedCardReq feedCardReq = new FeedCardReq();
            FeedCardReq.FeedCard feedCard = new FeedCardReq.FeedCard();
            feedCardReq.setFeedCard(feedCard);
            List<FeedCardReq.FeedCard.Link> links = Lists.newArrayList();
            feedCard.setLinks(links);
            links.add(new FeedCardReq.FeedCard.Link("时代的火车向前开1", "https://github.com/AnswerAIL", "https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png"));
            links.add(new FeedCardReq.FeedCard.Link("时代的火车向前开2", "https://github.com/AnswerAIL", "https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png"));
    
            dingTalkRobot.send(keyword, feedCardReq);
        }
    }
```

&nbsp;

### 个性化配置
#### 自定义消息体
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
> 仅支持text和markdown消息类型

&nbsp;

#### 自定义异常回调
```java
    @Configuration
    public class MyConfiguration {
        // 自定义异常回调函数
        @Bean
        public Notice notice() {
            return (dkExCallable) -> {
                // ...
            };
        }
    }
```

&nbsp;

#### 自定义签名体
 - 更改前： URL_PREV&sign=XXX&timestamp=XXX
 - 更改后： URL_PREV&timestamp=XXX&sign=XXX

```java
    @Data
    @AllArgsConstructor
    public class SignDTO extends SignBase {
        private String sign;
        private Long timestamp;
    
        @Override
        public String transfer() {
            StringBuilder signStr = new StringBuilder(SEPERATOR);
            signStr
                    .append("timestamp=").append(this.timestamp).append(SEPERATOR)
                    .append("sign=").append(this.sign);
            return signStr.toString();
        }
    }
```


```java
    @Configuration
    public class MyConfiguration {
        @Bean
        public DkSignAlgorithm<SignDTO> dkSignAlgorithm() {
            return new DkSignAlgorithm<SignDTO>() {
                @Override
                public SignDTO sign(String secret) throws Exception {
                    Long timestamp = System.currentTimeMillis();
                    // TODO 使用默认算法, 如果后期算法改变， 可在此更变签名算法
                    String sign = algorithm(timestamp, secret);
                    return new SignDTO(sign, timestamp);
                }
            };
        }
    }
```
> 该功能只针对后期版本变更进行扩展, 当前版本没有使用的必要

&nbsp;

#### 自定义处理ID生成器
```java
    @Configuration
    public class MyConfiguration {
        @Bean
        public DkIdGenerator dkIdGenerator() {
            return () -> {
                String dkid = null;
                // ...
                return dkid;
            };
        }  
    }
```
> 注意ID最好保证全局唯一

&nbsp;

#### 自定义异步执行结果回调处理器
```java
    @Configuration
    public class MyConfiguration {
        @Bean
        public DkCallable dkCallable() {
            return (dkid, result) -> {
                // ...
            };
        }    
    }
```
> 注意开启异步处理时使用内置线程池， 如果项目中有定义其他线程池配置, 注入线程池对象时请指定线程池名称。 如： `@Qualifier("executor")`

&nbsp;

#### 自定义线程池(可选)
```java
    @Configuration
    public class MyConfiguration {
        @Bean
        public Executor dingTalkExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            // ...
            return executor;
        }
    }
```

&nbsp;

#### 自定义http客户端(可选)
```java
    @Configuration
    public class MyConfiguration {
        @Bean
        public OkHttpClient okHttpClient() {
            // ...
        }
    }
```

&nbsp;

## References
 - [https://blog.csdn.net/u010979642/article/details/107566714](https://blog.csdn.net/u010979642/article/details/107566714)