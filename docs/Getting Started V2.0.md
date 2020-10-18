# V2.0快速入门文档

## 引入依赖
```xml
<dependency>
    <groupId>com.github.answerail</groupId>
    <artifactId>dingtalk-spring-boot-starter</artifactId>
    <version>2.0.4-RELEASE</version>
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

 

## 启动类添加注释@DingerScan

```java
@SpringBootApplication
@DingerScan(basePackages = "com.jaemon.gss.dinger")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

> 注意替换basePackages属性的值为自己项目中dinger接口层的包路径



## 定义Dinger接口

```java
package com.jaemon.gss.dinger;

public interface OrderDinger {
    
    // 发送text类型消息
    @DingerText(value = "订单号${orderNum}注册成功啦, 下单金额${amt}")
    DingTalkResult orderSuccess(@Parameter("orderNum") String orderNo, @Parameter("amt") BigDecimal amt);

    // 发送markdown类型消息
    @DingerMarkdown(
            value = "#### 注册失败啦\n - 订单号： ${orderNo}\n - 标识： ${flag}\n - 数量： ${num}",
            title = "下单结果反馈"
    )
    DingTalkResult orderFailed(String orderNo, int num, boolean flag);
}
```



## 测试使用

```java
@Component
@Slf4j
public class InitializingBeanExecute implements InitializingBean {

    @Autowired
    private OrderDinger orderDinger;

    @Override
    public void afterPropertiesSet() throws Exception {
        DingTalkResult dingTalkResult = orderDinger.orderSuccess( 
                "20200911001", 
                BigDecimal.valueOf(1000)
        );
        log.info(JSON.toJSONString(dingTalkResult));

        
        DingTalkResult dingTalkResult1 = orderDinger.orderFailed(
                "20200911002", 
                100, 
                false
        );
        log.info(JSON.toJSONString(dingTalkResult1));
    }
}
```



## 注意事项

如果按照以上配置后指定钉钉群并未收到消息， 排查原因时请关注下是否是`钉钉机器人有三种安全设置策略（自定义关键词、加签，IP地址段）`问题导致。

