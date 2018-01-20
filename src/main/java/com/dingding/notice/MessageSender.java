package com.dingding.notice;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

public interface MessageSender {

    public JSONObject sendMessage(String url,JSONObject content);

}
