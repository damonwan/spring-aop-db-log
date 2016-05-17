package com.damon.example.aoplog.entity;

import javax.persistence.Entity;

import com.damon.example.aoplog.annotation.Column;
import com.damon.example.aoplog.annotation.Table;

@Table(name = "user", description = "用户信息")
@Entity
public class User extends BaseEntity {

	private static final long serialVersionUID = 7218288159842628656L;
	
	@Column(name = "user_name", description = "用户名", required = true)
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
