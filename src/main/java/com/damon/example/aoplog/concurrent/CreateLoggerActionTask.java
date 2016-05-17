package com.damon.example.aoplog.concurrent;

import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;

import com.damon.example.aoplog.annotation.ModuleManage;
import com.damon.example.aoplog.annotation.ModuleOperation;
import com.damon.example.aoplog.entity.LoggerAction;
import com.damon.example.aoplog.enums.ModuleType;
import com.damon.example.aoplog.enums.OperationType;
import com.damon.example.aoplog.service.LoggerService;
import com.damon.example.aoplog.util.LoggerUtil;
import com.damon.example.aoplog.util.SpringContextUtil;

public class CreateLoggerActionTask implements Callable<LoggerAction>{

	private JoinPoint joinPoint;
	private String staffName;
	
	public CreateLoggerActionTask(JoinPoint joinPoint, String staffName){
		this.joinPoint = joinPoint;
		this.staffName = staffName;
	}
	
	@Override
	public LoggerAction call() throws Exception {
		return createLoggerAction();
	}
	
	private LoggerAction createLoggerAction(){
		ModuleManage moduleManage = LoggerUtil.getModuleManage(this.joinPoint);
		if(moduleManage.ignoreLog()){
			return null;
		}
		ModuleOperation moduleOperation = LoggerUtil.getModuleOperation(this.joinPoint); 
		if(moduleOperation.ignoreLog()){
			return null;
		}
		OperationType operationType = moduleOperation.value();
		if(OperationType.GET.equals(operationType)){
			return null;
		}
		//who this.staffName
		//when
		Date now = new Date();
		//where
		ModuleType moduleType = moduleManage.value();
		String moduleName = moduleType.getMessage();
		//do what
		String description = moduleOperation.description();
		String comment = String.format("【%s】于【%s】在【%s】模块执行了【%s】的操作", 
						this.staffName, 
						DateFormatUtils.format(now, "yyyy年MM月dd日 HH时mm分ss秒"),
						moduleName,
						description);
		LoggerAction loggerAction = new LoggerAction();
		loggerAction.setStaffName(staffName);
		loggerAction.setGmtCreate(now);
		loggerAction.setModuleName(moduleName);
		loggerAction.setOperationType(operationType.getCode());
		loggerAction.setOperationDescription(description);
		loggerAction.setComment(comment);
		LoggerService loggerService = SpringContextUtil.getBean(LoggerService.class);
		loggerService.createLoggerAction(loggerAction);
		return loggerAction;
	}

}
