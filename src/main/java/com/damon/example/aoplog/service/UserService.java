package com.damon.example.aoplog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damon.example.aoplog.annotation.SelectOne;
import com.damon.example.aoplog.entity.User;
import com.damon.example.aoplog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User add(User user) {
		return userRepository.save(user);
	}
	
	@SelectOne
	public User find(Long id) {
		return userRepository.findOne(id);
	}
	
	public User update(User user) {
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		userRepository.delete(id);
	}
}
