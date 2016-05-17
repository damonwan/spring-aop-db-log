package com.damon.example.aoplog.entity;

import javax.persistence.Entity;

import com.damon.example.aoplog.annotation.Column;
import com.damon.example.aoplog.annotation.Table;

/**
 * 数据库表操作日志
 * */
@Table(name = "logger_action", description = "行为操作日志")
@Entity
public class Logger extends BaseEntity {

	private static final long serialVersionUID = -8032769485958504395L;

	//操作ID.一个操作动作可能会涉及到多张表的日志
	@Column(name = "action_id", description = "操作行为ID")
	private Long actionId;

	// 日志类型 @LogType
	@Column(name = "log_type", description = "日志类型")
	private Integer logType;

	@Column(name = "table_name", description = "数据表名")
	private String tableName;

	@Column(name = "table_description", description = "数据表描述")
	private String tableDescription;

	@Column(name = "comment", description = "备注")
	private String comment;


	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
