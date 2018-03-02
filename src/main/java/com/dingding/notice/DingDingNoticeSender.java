package com.dingding.notice;

import com.alibaba.fastjson.JSONObject;
import com.dingding.util.JsonContentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DingDingNoticeSender implements MessageSender {
    @Autowired
    private HttpHelper httpHelper;
    @Autowired
    private JsonContentConverter jsonContentConverter;

    public JSONObject sendMessage(String url, JSONObject content) {
        Map<String,String> map = jsonContentConverter.getConvertMap();
        System.out.println("url:"+url);
        return (JSONObject) httpHelper.doPost(url,jsonContentConverter.convert(map,content));
    }
}
