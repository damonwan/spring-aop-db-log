package com.damon.example.aoplog.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.damon.example.aoplog.entity.Logger;
import com.damon.example.aoplog.entity.LoggerAction;
import com.damon.example.aoplog.entity.LoggerContent;
import com.damon.example.aoplog.enums.LogType;
import com.damon.example.aoplog.repository.LoggerActionRepository;
import com.damon.example.aoplog.repository.LoggerContentRepository;
import com.damon.example.aoplog.repository.LoggerRepository;
import com.damon.example.aoplog.util.LoggerUtil;
import com.google.common.base.Optional;

@Service
public class LoggerService {

private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LoggerService.class);
	
	@Resource
	private LoggerRepository loggerRepository;
	
	@Resource
	private LoggerContentRepository loggerContentRepository;

	@Resource
	private LoggerActionRepository loggerActionRepository;
	
	public Logger getLoggerById(Long id) {
		return loggerRepository.findOne(id);
	}
	
	@Transactional
	public <T> void createLogger(LogType logType, Future<LoggerAction> future,
			T oldObject, T newObject, String comment) {
		if(future == null){
			return;
		}
		Logger logger = LoggerUtil.getLogger(logType, oldObject, newObject, comment);
		if(logger == null){
			return;
		}
		
		List<LoggerContent> contents = LoggerUtil.getLoggerContents(oldObject, newObject);

		if(!LoggerUtil.validContents(contents)){
			return;
		}
		Optional<LoggerAction> optional = null;
		try {
			optional = Optional.fromNullable(future.get());
		} catch (Exception e) {
			LOG.info("获取操作行为日志失败。future->{}, error->{}", future, e);
		}
		Long actionId = null;
		final Date now;
		if(optional.isPresent()){
			LoggerAction action = optional.get();
			actionId = action.getId();
			now = action.getGmtCreate();
		}else{
			now = new Date();
		}
		logger.setActionId(actionId);
		logger.setGmtCreate(now);
		loggerRepository.save(logger);
		contents.stream().forEach(content -> {
			content.setLoggerId(logger.getId());
			content.setGmtCreate(now);
			loggerContentRepository.save(content);
		});
	}
	
//	private Map<String, Object> getQueryMap(String staffName, String moduleName, Integer operationType, String description, Date createStartTime,
//			Date createEndTime, String orderBy, String sort, int offset, int count){
//		Map<String, Object> query = Maps.newHashMap();
//		query.put("staffName", ColumnUtil.getNullIfEmpty(staffName));
//		query.put("moduleName", ColumnUtil.getNullIfEmpty(moduleName));
//		query.put("operationType", operationType);
//		query.put("operationDescription", ColumnUtil.getNullIfEmpty(description));
//		query.put("createStartTime", createStartTime);
//		query.put("createEndTime", createEndTime);
//		query.put("orderBy", ColumnUtil.getOrderBy(orderBy, Logger.class));
//		query.put("sort", ColumnUtil.getSortDirection(sort));
//		query.put("offset", offset);
//		query.put("count", count);
//		return query;
//	}

	public LoggerContent getLoggerContentById(Long id) {
		return loggerContentRepository.findOne(id);
	}

	public int getCountOfLoggers(Long actionId) {
		return loggerRepository.countByActionId(actionId);
	}

	public int getCountOfLoggerContents(Long loggerId) {
		return loggerContentRepository.countByLoggerId(loggerId);
	}

	public List<Logger> getLoggers(Long actionId) {
		return loggerRepository.findByActionId(actionId);
	}

	public List<LoggerContent> getLoggerContents(Long loggerId) {
		return loggerContentRepository.findByLoggerId(loggerId);
	}

	@Transactional
	public void createLoggerAction(LoggerAction loggerAction) {
		loggerActionRepository.save(loggerAction);
	}

	public LoggerAction getLoggerActionById(Long id) {
		return loggerActionRepository.findOne(id);
	}

//	public int getCountOfLoggerActions(String staffName, String moduleName, Integer operationType, String description, 
//			Date createStartTime, Date createEndTime) {
//		Map<String, Object> map = getQueryMap(staffName, moduleName, operationType, description, createStartTime, createEndTime, null, null, 0, 0);
//		return loggerRepository.getCountOfLoggerActions(map);
//	}
//
//	public List<LoggerAction> getLoggerActions(String staffName, String moduleName, Integer operationType, String description,
//			Date createStartTime, Date createEndTime,
//			String orderBy, String sort, int offset, int count) {
//		Map<String, Object> map = getQueryMap(staffName, moduleName, operationType, description, createStartTime, createEndTime, orderBy, sort, offset, count);
//		return loggerRepository.getLoggerActions(map);
//	}
	
	@Transactional
	public void deleteLoggerActionById(Long id){
		loggerActionRepository.deleteById(id);
	}

	@Transactional
	public void updateLoggerAction(LoggerAction loggerAction) {
		loggerActionRepository.save(loggerAction);
	}
	
	@Transactional
	public void processActionComment(Long actionId){
		LoggerAction loggerAction = this.getLoggerActionById(actionId);
		if(loggerAction == null){
			return;
		}
		List<Logger> loggers = this.getLoggers(actionId);
		final StringBuilder builder;
		if(StringUtils.isNotBlank(loggerAction.getComment())){
			builder = new StringBuilder(loggerAction.getComment());
		}else{
			builder = new StringBuilder();
		}
		
		builder.append("本次操作涉及到如下内容：");
		final int[] index = {0};
		loggers.stream().forEach(logger -> {
			builder.append(index[0] + 1)
					.append("，")
					.append(logger.getComment())
					.append(" 【")
					.append(logger.getTableDescription())
					.append("】：");
			index[0]++;
			getLoggerContents(logger.getId()).stream().forEach(content -> {
					builder.append("[")
							.append(content.getColumnDescription())
							.append("]从“")
							.append(getValueDescription(content.getOldValue()))
							.append("”变为“")
							.append(getValueDescription(content.getNewValue()))
							.append("”");
					builder.append("。");
					builder.append("\n");
				});
		});
		loggerAction.setComment(builder.toString());
		this.updateLoggerAction(loggerAction);
	}

	private <T> String getValueDescription(T t){
		if(t == null || (t instanceof String && StringUtils.isBlank(t.toString()))){
			return "空值";
		}
		return t.toString();
	}

	@Transactional
	public void clearNoUsefulLoggerAction() {
//		loggerRepository.clearNoUsefulLoggerAction();
	}

}
