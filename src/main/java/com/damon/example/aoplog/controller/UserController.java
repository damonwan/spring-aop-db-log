package com.damon.example.aoplog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.damon.example.aoplog.entity.User;
import com.damon.example.aoplog.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(String userName) {
		User user = new User();
		user.setUserName(userName);
		userService.add(user);
		return user.toString();
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public String query(int id) {
		User user = userService.find(id);
		return user.toString();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(int id, String userName) {
		User user = new User();
		user.setId(id);
		user.setUserName(userName);
		userService.update(user);
		return user.toString();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int id) {
		userService.delete(id);
		return "ok";
	}
}
