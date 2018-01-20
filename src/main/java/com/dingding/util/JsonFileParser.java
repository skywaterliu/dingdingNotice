package com.dingding.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.*;

public class JsonFileParser {

    public static JSONObject getJsonObject(String path){
        if(path == null) return null;
        File file = new File(path);
        return getJsonObject(file);
    }

    public static JSONObject getJsonObject(File file) {
        if(file == null) return null;

        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JSONObject result = null;

        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int read = -1;
            while((read = inputStream.read(bytes)) > -1){
                outputStream.write(bytes,0,read);
            }

            result = JSONObject.parseObject(new String(outputStream.toByteArray(),"UTF-8"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件未找到:\n"+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("读取失败:\n"+e.getMessage());
        }

        return result;
    }

}
