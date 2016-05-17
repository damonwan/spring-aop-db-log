package com.damon.example.aoplog.entity;

import javax.persistence.Entity;

import com.damon.example.aoplog.annotation.Column;
import com.damon.example.aoplog.annotation.Table;

/**
 * 操作行为日志 一个操作行为可能会影响多张数据表.
 * */
@Table(name = "logger_action", description = "行为操作日志")
@Entity
public class LoggerAction extends BaseEntity {
	
	private static final long serialVersionUID = 5390808525971198608L;

	@Column(name = "staff_name", description = "操作人域账号")
	private String staffName;

	@Column(name = "module_name", description = "模块名称")
	private String moduleName;

	// @OperationType
	@Column(name = "operation_type", description = "操作类型")
	private Integer operationType;

	@Column(name = "operation_description", description = "操作描述")
	private String operationDescription;

	// 用来拼接详细的日志信息
	@Column(name = "comment", description = "备注")
	private String comment;

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperationDescription() {
		return operationDescription;
	}

	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

}
