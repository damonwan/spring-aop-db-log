package com.damon.example.aoplog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.damon.example.aoplog.entity.LoggerContent;

@Repository
public interface LoggerContentRepository extends CrudRepository<LoggerContent, Long>{

	int countByLoggerId(Long longgerId);

	List<LoggerContent> findByLoggerId(Long loggerId);
}
