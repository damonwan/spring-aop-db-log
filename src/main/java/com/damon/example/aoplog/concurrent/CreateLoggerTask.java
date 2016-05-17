package com.damon.example.aoplog.concurrent;

import java.util.concurrent.Future;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.damon.example.aoplog.entity.LoggerAction;
import com.damon.example.aoplog.enums.LogType;
import com.damon.example.aoplog.service.LoggerService;
import com.damon.example.aoplog.util.SpringContextUtil;

public class CreateLoggerTask implements Runnable{

	private Future<LoggerAction> future;
	private LogType logType; 
	private Object before; 
	private Object after;
	private String comment;
	
	public CreateLoggerTask(Future<LoggerAction> future,
					LogType logType,
					Object before,
					Object after,
					String comment){
		this.future = future;
		this.logType = logType;
		this.before = before;
		this.after = after;
		this.comment = comment;
	}
	
	@Override
	public void run() {
		int range = 0;
		Object[] befores;
		Object[] afters;
		
		if(before != null && before.getClass().isArray()){
			befores = (Object[])before;
			range = befores.length;
		}else{
			befores = null;
		}
		
		if(after != null && after.getClass().isArray()){
			afters = (Object[])after;
			range = afters.length;
		}else{
			afters = null;
		}
		LoggerService loggerService = SpringContextUtil.getBean(LoggerService.class);
		if(range != 0){
			IntStream.range(0, range)
					.forEach(i -> {
						loggerService.createLogger(logType, future, getObject(befores, i), getObject(afters, i), comment);
					});
		}else{
			loggerService.createLogger(logType, future, before, after, comment);
		}
	}
	
	private Object getObject(Object[] array, int index){
		if(ArrayUtils.isEmpty(array)){
			return null;
		}
		return array[index];
	}

}
