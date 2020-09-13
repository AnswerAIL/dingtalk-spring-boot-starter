# V1.0快速入门文档

## 引入依赖
```xml
<dependency>
    <groupId>com.github.answerail</groupId>
    <artifactId>dingtalk-spring-boot-starter</artifactId>
    <version>1.0.5-RELEASE</version>
</dependency>

<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>3.10.0</version>
</dependency>
```



## 配置文件

```yml
spring:
  dingtalk:
    # token-id的值需要自己获取后替换
    token-id: 92dbeb7bc28894c3bdcc3d13590168ad309974327b5f427cd85f9025ebb520ai
    # 必填，建议： 可以用 project-id 作为 钉钉机器人设置安全策略-自定义关键词(自定义关键词有长度限制，可自行截取前缀或后缀)
    project-id: ${spring.application.name}
    # 钉钉机器人-加签值，开启加签时使用
#    secret: sssss
```

> [token-id获取方式](https://ding-doc.dingtalk.com/doc#/serverapi3/iydd5h/26eaddd5)： token-id的值就是机器人的Webhook地址中access_token的值。




## 测试使用

```java
@Component
@Slf4j
public class InitializingBeanExecute implements InitializingBean {
    @Autowired
    private DingTalkSender dingTalkSender;

    @Override
    public void afterPropertiesSet() throws Exception {
        String keyword = "DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM";
        String subTitle = "服务启动通知";
        String content = "服务启动啦。。。";

        // text类型
        dingTalkSender.send(MsgTypeEnum.TEXT, keyword, subTitle, content);

        // markdown类型
        dingTalkSender.send(MsgTypeEnum.MARKDOWN, keyword, subTitle, content);
    }
}
```



## 注意事项

如果按照以上配置后指定钉钉群并未收到消息， 排查原因时请关注下是否是`钉钉机器人有三种安全设置策略（自定义关键词、加签，IP地址段）`问题导致。

