package com.dingding.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * json对象内的某些字符进行变量替换，
 * 默认${currentTime},${currentDate}
 */
@Service
public class JsonContentConverter implements VariableConverter<JSONObject> {

    private Map<String,String> convertMap = new HashMap<String, String>(){
        {
            this.put("${currentTime}",DateUtil.getDateFormat(new Date(),DateUtil.DATETIME_DEFAULT_FORMAT));
            this.put("${currentDate}",DateUtil.getDateFormat(new Date(),DateUtil.DATE_DEFAULT_FORMAT));
        }
    };


    public JSONObject convert(Map<String, String> map, JSONObject target) {
        String jsonString = target.toJSONString();
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            jsonString = jsonString.replace(entry.getKey(),entry.getValue());
        }
        return JSONObject.parseObject(jsonString);
    }

    public Map<String, String> getConvertMap() {
        return convertMap;
    }

    public  void setConvertMap(Map<String, String> convertMap) {
        if(convertMap != null && convertMap.size()>0){
            this.convertMap.putAll(convertMap);
        }
    }
}
