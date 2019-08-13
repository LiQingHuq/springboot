package com.scetc.web.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.recompile;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JsonUtils {
    private static ObjectMapper objectMapper=new ObjectMapper();
    /**
     * 方法功能描述:json序列化
     * @Author: lqh
     * @param o
     * @Date: 2019-08-13 02:44:46
     * @return:
    */
    public static String toJson(Object o){
        Writer writer=new StringWriter();
          try{
              ObjectMapper objectMapper=new ObjectMapper();
              objectMapper.writeValue(writer,o);
          }catch (Exception e){
              e.printStackTrace();
          }
        return  writer.toString();
    }
    /** 
     * 方法功能描述: json反序列化
     * @Author: lqh
     * @Date: 2019-08-13 02:46:57
     * @return: 
    */
    public static<T> T jsonToEntity(String str,Class<T> clazz)throws IOException {
       return objectMapper.readValue(str,clazz);
    }
    /** 
     * 方法功能描述: json反序列化集合
     * @Author: lqh
     * @Date: 2019-08-13 02:51:57
     * @return: 
    */
    public static<T> T jsonToListEntity(String  str, TypeReference<T> reference)throws IOException{
       return objectMapper.readValue(str,reference);
    }
}
