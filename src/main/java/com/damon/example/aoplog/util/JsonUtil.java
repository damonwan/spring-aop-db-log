package com.damon.example.aoplog.util;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

public class JsonUtil {
	
	private JsonUtil(){}

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public static <T> T deserializeJson(String json, Class<T> clazz) {
        try {
        	if(json != null){
        		return mapper.readValue(json, clazz);
        	}
        } catch (IOException e) {
        	LOG.error(e.getMessage(), e);
        } catch (Exception e) {
        	LOG.warn(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T deserializeJson(String json, TypeReference<T> typeReference) {
        try {
        	if(json != null){
        		return mapper.readValue(json, typeReference);
        	}
        } catch (IOException e) {
        	LOG.error(e.getMessage(), e);
        } catch (Exception e) {
        	LOG.warn(e.getMessage(), e);
        }
        return null;
    }

    public static <T> String serializeObject(T json) {
        try {
            return mapper.writeValueAsString(json);
        } catch (IOException e) {
        	LOG.error(e.getMessage(), e);
        } catch (Exception e) {
        	LOG.warn(e.getMessage(), e);
        }
        return "";
    }
    
	public static <T> Map<String, Object> object2Map(T t){
    	if(t == null){
			return Maps.newHashMap();
		}
		String json = serializeObject(t);
		return deserializeJson(json, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * 对象的深度copy
     * @param t 原来的对象
     * @return 新对象
     */
    public static <T> T deepCopy(T t){
    	String json = serializeObject(t);
		return deserializeJson(json, new TypeReference<T>() {});
    }
}
