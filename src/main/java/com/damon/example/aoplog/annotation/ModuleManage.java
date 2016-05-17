package com.damon.example.aoplog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.damon.example.aoplog.enums.ModuleType;

/**
 * 模块管理注解,用于Controller
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleManage {

	/**
	 * 模块分类，详见 {@link ModuleType}
	 * */
	public ModuleType value();
	
	/**
	 * 是否不记录日志，默认false
	 * */
	public boolean ignoreLog() default false;
}
