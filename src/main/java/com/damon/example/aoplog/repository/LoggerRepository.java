package com.damon.example.aoplog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.damon.example.aoplog.entity.Logger;

@Repository
public interface LoggerRepository extends CrudRepository<Logger, Long>{
	
	int countByActionId(Long actionId);

	List<Logger> findByActionId(Long actionId);

}
