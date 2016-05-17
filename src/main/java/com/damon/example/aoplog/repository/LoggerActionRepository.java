package com.damon.example.aoplog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.damon.example.aoplog.entity.LoggerAction;

@Repository
public interface LoggerActionRepository extends CrudRepository<LoggerAction, Long>{

	void deleteById(Long id);

}
