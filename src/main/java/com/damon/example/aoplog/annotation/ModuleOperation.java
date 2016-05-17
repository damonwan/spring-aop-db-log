package com.damon.example.aoplog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.damon.example.aoplog.enums.OperationType;

/**
 * 模块操作管理注解,用于Controller中的方法
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleOperation {

	/**
	 * 操作类型，详见 {@link OperationType}
	 * */
	public OperationType value();
	
	/**
	 * 操作描述
	 * */
	public String description() default "";
	
	/**
	 * 是否不记录日志，默认false
	 * */
	public boolean ignoreLog() default false;
}
