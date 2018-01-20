# 钉钉机器人通知

乱七八糟。。
项目生产环境数据库需要定时备份通知，配合windows定时任务使用  bat命令添加在备份脚本中的exp执行后

1. bat的main函数传参为对应的json文件名 不需要.json后缀

2. 目录结构 <br/>
    |- jsonConfig <br/>
    |- lib <br/>
    |- config.properties <br/>
    |- dingding.jar <br/>
    |- test.bat

3. maven获取依赖<br/>
在pom.xml目录下，执行1
```mermaid
mvn dependency:copy-dependencies -DoutputDirectory=lib   -DincludeScope=compile
