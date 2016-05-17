package com.damon.example.aoplog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.damon.example.aoplog.annotation.ModuleManage;
import com.damon.example.aoplog.annotation.ModuleOperation;
import com.damon.example.aoplog.entity.User;
import com.damon.example.aoplog.enums.ModuleType;
import com.damon.example.aoplog.enums.OperationType;
import com.damon.example.aoplog.service.UserService;

@RestController
@ModuleManage(ModuleType.USER)
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModuleOperation(value = OperationType.ADD, description = "创建用户信息")
	@RequestMapping("/add")
	@ResponseBody
	public String add(String userName) {
		User user = new User();
		user.setUserName(userName);
		userService.add(user);
		return user.toString();
	}
	
	@ModuleOperation(value = OperationType.GET, description = "查询用户信息")
	@RequestMapping("/query")
	@ResponseBody
	public String query(int id) {
		User user = userService.find((long) id);
		return user.toString();
	}
	
	@ModuleOperation(value = OperationType.UPDATE, description = "修改用户信息")
	@RequestMapping("/update")
	@ResponseBody
	public String update(int id, String userName) {
		User user = new User();
		user.setId((long) id);
		user.setUserName(userName);
		userService.update(user);
		return user.toString();
	}
	
	@ModuleOperation(value = OperationType.DELETE, description = "删除用户信息")
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		userService.delete((long) id);
		return "ok";
	}
}
