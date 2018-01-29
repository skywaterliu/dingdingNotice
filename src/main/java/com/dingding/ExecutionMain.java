package com.dingding;

import com.alibaba.fastjson.JSONObject;
import com.dingding.notice.DingDingNoticeSender;
import com.dingding.notice.MessageSender;
import com.dingding.util.CustomizedPropertyConfigurer;
import com.dingding.util.JsonContentConverter;
import com.dingding.util.JsonFileScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ExecutionMain {
    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
    }

    /**
     * 接收对应的配置文件名，不带后缀。
     * 对应一个json配置文件，包含了发送信息
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("jar执行目录："+ExecutionMain.getProjectPath());

        if(args.length == 0) throw new RuntimeException("未传入参数");

        MessageSender messageSender = context.getBean(DingDingNoticeSender.class);
        JsonFileScanner scanner = context.getBean(JsonFileScanner.class);

        Properties properties = loadProperties();
        //获取发送信息
        JSONObject messageInfo = scanner.findJsonFromDirectory(args[0],loadJsonConfigDir());
        //发送信息
        messageSender.sendMessage(properties.getProperty("url"),messageInfo);
    }

    /**
     * 加载jar包所在目录的
     * @return
     */
    private static Properties loadProperties(){
        Properties properties = new Properties();
        try {
            String path = ExecutionMain.getProjectPath() + File.separator + "config.properties";
            System.out.println("配置文件路径："+path);
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("config.properties读取失败");
        }
        return properties;
    }

    private static String loadJsonConfigDir(){
        return ExecutionMain.getProjectPath() + File.separator + "jsonConfig";
    }

    private static String getProjectPath() {
        java.net.URL url = ExecutionMain.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

}
