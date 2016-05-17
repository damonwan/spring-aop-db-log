package com.damon.example.aoplog.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 模块分类
 * */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ModuleType implements EnumBase<String>{
	USER("user", "用户管理");
	
	private String code;
	private String message;
	
	ModuleType(String code, String message){
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public static ModuleType getModuleType(String code){
		for (ModuleType ul : values()) {
            if (ul.getCode().equals(code)) {
                return ul;
            }
        }
        return null;
	}

}
