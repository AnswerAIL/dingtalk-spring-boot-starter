# dingtalk-spring-boot-starter

集成钉钉机器人实现消息通知

&nbsp;

## 版本变更说明
| 版本号        | 日期       | 变更说明                                   |
| ------------- | ---------- | ------------------------------------------ |
| 1.0.0-RELEASE | 2020-07-21 | 初始化版本                                 |
| 1.0.1-RELEASE | 2020-07-22 | + 支持通知消息体自定义<br />+ 支持异常回调 |
|     -         |            |                                            |


&nbsp;

## 使用说明
```xml
    <!-- 方式1： 本地jar包 -->
    <dependency>
        <groupId>com.github.answerail</groupId>
        <artifactId>dingtalk-spring-boot-starter</artifactId>
        <version>1.0.0-RELEASE</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/lib/dingtalk-spring-boot-starter-1.0.0-RELEASE.jar</systemPath>
    </dependency>
    

    <!-- 方式2： 拉取maven仓库获取jar -->
    <dependency>
        <groupId>com.github.answerail</groupId>
        <artifactId>dingtalk-spring-boot-starter</artifactId>
        <version>${answerail-dingtalk.version}</version>
    </dependency>
```
**answerail-dingtalk.version目前支持版本**
 - 1.0.0-RELEASE
 - 1.0.1-RELEASE

***

```java
    public class Demo {
        @Autowired
        private DingTalkRobot dingTalkRobot;
        
        public void test() {
            // 普通方式通知
            dingTalkRobot.send("DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动啦。。。");

            // 通知并@135XXXXXXXX用户
            dingTalkRobot.send("DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动啦。。。", Lists.newArrayList("135XXXXXXXX"));

            // 通知并@所有
            dingTalkRobot.sendAll("DYZ3AALTRBD2AIDLL0Y3EQ4TYGLJDUM", "服务启动啦。。。");
        }         
    }
```