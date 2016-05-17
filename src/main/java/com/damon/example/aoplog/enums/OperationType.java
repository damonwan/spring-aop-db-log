package com.damon.example.aoplog.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 操作类型:查看,增加,修改,删除
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationType implements EnumBase<Integer>{

	GET(1, "查看"),
	ADD(2, "添加"),
	UPDATE(3, "修改"),
	DELETE(4, "删除");
	
	private int code;
	private String message;
	
	OperationType(int code, String message){
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
	
	public static OperationType getOperationType(Integer code) {
        for (OperationType ul : values()) {
            if (ul.getCode().equals(code)) {
                return ul;
            }
        }
        return null;
    }
}
