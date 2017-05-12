package com.pipi.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by yahto on 07/05/2017.
 */
public class JsonUtils {
	private static final Log log = LogFactory.getLog(JsonUtils.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**返回ObjectMap对象，方便其他类使用*/
	public static ObjectMapper getObjectMapper(){
		return mapper;
	}

	/**将对象，Map，List等转换成json字符串*/
	public static String jsonFromObject(Object object) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, object);
		} catch (Exception e) {
			log.error("Unable to serialize to json: " + object, e);
			return null;
		}
		return writer.toString();
	}

	/**将json字符串转换成Map对象*/
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
			/**把json转成HashMap*/
			hashMap = mapper.readValue(jsonStr,
					new TypeReference<Map<String, Object>>() {
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
	}

	/**将json字符串转换成List对象*/
	public static List<?> jsonToList(String jsonStr,Class<?> clazz) {
		JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
		List<?> list = null;
		try {
			/**把json转成List*/
			list = mapper.readValue(jsonStr,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**将json字符串转换成List对象*/
	public static List<?> jsonToList(String jsonStr) {
		List<?> list = new ArrayList<Object>();
		try {
			/**把json转成List*/
			list = mapper.readValue(jsonStr,
					new TypeReference<List<?>>(){});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// 解析json对象
	public static JsonNode readTree(String content){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = mapper.readTree(content);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return rootNode;
	}
	/**
	* 使用Jackson 数据绑定 将对象转换为 json字符串
	* 
	* 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式 
	* @param obj
	* @return
	*/
	public static String toJsonString(Object obj) {
	 try {
	      return mapper.writeValueAsString(obj);
	      } catch (JsonGenerationException e) {
	    	  log.error("转换为json字符串失败" + e.toString());
           } catch (JsonMappingException e) {
        	   log.error("转换为json字符串失败" + e.toString());
	       } catch (IOException e) {
	    	   log.error("转换为json字符串失败" + e.toString());
	      }
	      return null;
	     }
	
}