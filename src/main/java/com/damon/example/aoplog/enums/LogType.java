package com.damon.example.aoplog.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 日志操作类型:增加,修改,删除
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LogType implements EnumBase<Integer>{

	ADD(1, "增加"),
	UPDATE(2, "修改"),
	DELETE(3, "删除");
	
	private int code;
	private String message;
	
	LogType(int code, String message){
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
	public static LogType getLogType(Integer code){
		for (LogType ul : values()) {
            if (ul.getCode().equals(code)) {
                return ul;
            }
        }
        return null;
	}
}
