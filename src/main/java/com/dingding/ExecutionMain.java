package com.dingding;

import com.alibaba.fastjson.JSONObject;
import com.dingding.notice.DingDingNoticeSender;
import com.dingding.notice.MessageSender;
import com.dingding.util.CustomizedPropertyConfigurer;
import com.dingding.util.JsonContentConverter;
import com.dingding.util.JsonFileScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class ExecutionMain {
    private static ApplicationContext context;
    private static JsonContentConverter jsonContentConverter;
    private static MessageSender messageSender;
    private static JsonFileScanner scanner;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        messageSender = context.getBean(DingDingNoticeSender.class);
        scanner = context.getBean(JsonFileScanner.class);
        jsonContentConverter = context.getBean(JsonContentConverter.class);
    }

    /**
     * 接收对应的配置文件名，不需要后缀。
     * 对应一个json配置文件，包含了发送信息
     * @param args
     */
    public static void main(String[] args) {
        if(args.length == 0) throw new RuntimeException("未传入参数");

        String jsonFileDir = (String) CustomizedPropertyConfigurer.getContextProperty("sendInfoJsonFileDir");
        String sendURL = (String) CustomizedPropertyConfigurer.getContextProperty("url");

        JSONObject messageInfo = scanner.findJsonFromDirectory(args[0],jsonFileDir);

        messageSender.sendMessage(sendURL,messageInfo);
    }

}
