package com.dingding.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;

@Service
public class JsonFileScanner {

    public JSONObject findJsonFromDirectory(String targetJsonName,String jsonFileDir){
        if(jsonFileDir == null || jsonFileDir.equals("")) throw new RuntimeException("路径为空");

        System.out.println("jsonConfig路径:"+jsonFileDir);
        System.out.println("目标json:"+targetJsonName);

        File targetDir = new File(jsonFileDir);
        if(!targetDir.isDirectory()) throw new RuntimeException("该路径不是文件夹");

        File[] fileList = targetDir.listFiles(new JsonFileFilter());
        for(File file : fileList){
            if(matchFile(targetJsonName,file)){
                return JsonFileParser.getJsonObject(file);
            }
        }

        return null;
    }

    private boolean matchFile(String targetJsonName, File file) {
        String fileName = file.getName();
        return fileName.substring(0,fileName.toLowerCase().indexOf(".json")).equals(targetJsonName);
    }

    private class JsonFileFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            if(name.toLowerCase().endsWith(".json")){
                return true;
            }
            return false;
        }
    }

}
