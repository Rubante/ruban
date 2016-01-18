package com.ruban.framework.core.utils.commons;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * JSON工具类。
 * 
 * @author RZTAO
 *
 */
public class JsonUtils {
	private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	private JsonUtils(){}

	/**
	 * 将对象装换成Json。
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 将json转换成对象。
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static<T> T fromJson(String json, Class<? extends T>type){
		return JSON.parseObject(json, type);
	}
	
	/**
	 * 获取json中某项值。
	 * 
	 * @param json
	 * @param attr
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static String getString(String json, String attr) throws IOException{
		JSONObject jsonObj = JSON.parseObject(json);
		return jsonObj.getString(attr);
	}
	
	/**
	 * 获取json中某项值。
	 * 
	 * @param json
	 * @param attr
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static Long getLong(String json, String attr) throws IOException{
		JSONObject jsonObj = JSON.parseObject(json);
		return jsonObj.getLong(attr);
	}
}
