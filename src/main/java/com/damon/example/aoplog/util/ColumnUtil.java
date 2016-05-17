package com.damon.example.aoplog.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damon.example.aoplog.annotation.Column;
import com.damon.example.aoplog.entity.BaseEntity;
import com.google.common.collect.Lists;

public class ColumnUtil {
	
	private ColumnUtil(){}
	
	private static final Logger LOG = LoggerFactory.getLogger(ColumnUtil.class);
	
	private static Map<Class<?>, Map<String, String>> tableMap = new HashMap<>();
	
	/**
	 * 根据传入的field name,返回对应的字段名称,如果不存在,则返回null
	 * 使用 double check保证多线程安全
	 * */
	public static <T extends BaseEntity> String getOrderBy(String orderBy, Class<T> clazz){
		if(orderBy == null || clazz == null){
			return null;
		}
		if(!tableMap.containsKey(clazz)){
			synchronized (clazz) {
				if(!tableMap.containsKey(clazz)){
				    List<Field> fields = Lists.newArrayList(clazz.getDeclaredFields());
					List<Field> superFields = Lists.newArrayList(clazz.getSuperclass().getDeclaredFields());
					fields.addAll(superFields);
					
					Map<String, String> columnMap = fields.stream()
						.filter(field -> {
							return field.isAnnotationPresent(Column.class);
						})
						.collect(
							Collectors.toMap(
								field -> field.getName(), 
								field -> field.getAnnotation(Column.class).name()
							)
						);
					tableMap.put(clazz, columnMap);
					LOG.info("tableMap body: {}",tableMap);
				}
			}
		}
		if(tableMap.containsKey(clazz) && tableMap.get(clazz).containsKey(orderBy)){
			return tableMap.get(clazz).get(orderBy);
		}
		return null;
	}
	
	public static String getSortDirection(String sort){
		if(sort == null){
			return null;
		}
		if("DESC".equalsIgnoreCase(sort)){
			return "DESC";
		}
		return "ASC";
	}
	
	public static String getNullIfEmpty(String value){
		if(StringUtils.isBlank(value)){
			return null;
		}
		return value;
	}
}
