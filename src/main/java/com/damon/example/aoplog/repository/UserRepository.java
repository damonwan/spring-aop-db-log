package com.damon.example.aoplog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.damon.example.aoplog.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}
