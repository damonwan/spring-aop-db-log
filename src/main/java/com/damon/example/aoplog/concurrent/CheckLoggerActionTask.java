package com.damon.example.aoplog.concurrent;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damon.example.aoplog.entity.LoggerAction;
import com.damon.example.aoplog.service.LoggerService;
import com.damon.example.aoplog.util.SpringContextUtil;

public class CheckLoggerActionTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(CheckLoggerActionTask.class);
	
	private Future<LoggerAction> future;
	private List<Future<?>> futureList;

	public CheckLoggerActionTask(List<Future<?>> futureList,
			Future<LoggerAction> future) {
		this.futureList = futureList;
		this.future = future;
	}

	@Override
	public void run() {
		checkLoggerAction();
	}

	private void checkLoggerAction() {
		try {
			if(future == null){
				return;
			}
			LoggerAction action = future.get();
			if (action == null) {
				return;
			}
			Long actionId = action.getId();
			if(!CollectionUtils.isEmpty(futureList)){
				for(Future<?> tempFuture : futureList){
					tempFuture.get();
				}
			}
			LoggerService loggerService = SpringContextUtil.getBean(LoggerService.class);
			int count = loggerService.getCountOfLoggers(actionId);
			if (count == 0) {
				loggerService.deleteLoggerActionById(actionId);
			} else {
				loggerService.processActionComment(actionId);
			}
			loggerService.clearNoUsefulLoggerAction();
		} catch (Exception e) {
			LOG.info("checkLoggerAction exception, error->{}, message->", e, e.getMessage());
		}
	}
}
