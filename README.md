# 钉钉机器人通知

1. bat的main函数传参为对应的json文件名 不需要.json后缀

2. 目录结构 <br/>
    |- jsonConfig <br/>
    |- lib <br/>
    |- config.properties <br/>
    |- dingding.jar <br/>
    |- test.bat

3. maven获取依赖<br/>
在pom.xml目录下，执行
```mermaid
mvn dependency:copy-dependencies -DoutputDirectory=lib   -DincludeScope=compile
