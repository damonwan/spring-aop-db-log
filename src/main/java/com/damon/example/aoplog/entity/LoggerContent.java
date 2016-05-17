package com.damon.example.aoplog.entity;

import javax.persistence.Entity;

import com.damon.example.aoplog.annotation.Column;
import com.damon.example.aoplog.annotation.Table;

@Table(name = "logger_content", description = "字段操作日志")
@Entity
public class LoggerContent extends BaseEntity {

	private static final long serialVersionUID = 4947544502247302538L;

	// 对应的日志ID
	@Column(name = "logger_id", description = "相关日志ID")
	private Long loggerId;

	// 字段名(数据库里字段名)
	@Column(name = "column_name", description = "字段名")
	private String columnName;

	// 字段描述(举例:由于数据库里字段名是business_id这样的,看不懂,因此为字段起了一个名字,如企业用户ID)
	@Column(name = "column_description", description = "字段描述")
	private String columnDescription;

	@Column(name = "old_value", description = "旧值")
	private String oldValue;

	@Column(name = "new_value", description = "新值")
	private String newValue;
	
	@Column(name = "comment", description = "备注")
	private String comment;

	public Long getLoggerId() {
		return loggerId;
	}

	public void setLoggerId(Long loggerId) {
		this.loggerId = loggerId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDescription() {
		return columnDescription;
	}

	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
