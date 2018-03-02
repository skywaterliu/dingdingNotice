# 钉钉机器人通知

项目生产环境数据库需要定时备份通知，配合windows定时任务使用

1. 目录结构 <br/>
    |- jsonConfig <br/>
    |- lib <br/>
    |- config.properties <br/>
    |- dingding.jar <br/>
    |- test.bat

2. bat脚本中执行完备份语句后  添加 
``` bat
java -cp "[path of jar]/dingding.jar;[path of jar]/lib/*" com.dingding.ExecutionMain test
```

3. bat执行的java main函数传参为对应该bat要发送的信息json文件名 不需要.json后缀

4. windows定时任务添加bat定时备份

5. config.properties中配置
    1. url
        机器人的地址
    2. sendInfoJsonFileDir
        发送的消息json文件目录
        

> maven获取jar包依赖
> 在pom.xml目录下，执行
> ``` maven
> mvn dependency:copy-dependencies -DoutputDirectory=lib   -DincludeScope=compile
> ```


