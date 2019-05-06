package com.mimacom.task.repository;

import org.springframework.data.repository.CrudRepository;

import com.mimacom.task.model.Task;


public interface TaskRepository extends CrudRepository<Task, Integer> {}
