package com.mimacom.task.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimacom.task.constants.Constants;
import com.mimacom.task.model.Task;
import com.mimacom.task.repository.TaskRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("task")
@Api(value="task")
public class ControllerTask {
	
	@Autowired
	private TaskRepository repository;
	 
	
	@GetMapping()
	public ResponseEntity<List<Task>> getListTasks() {
		 
		List<Task>tasks = (List<Task>) repository.findAll();
		
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("id")Integer id) {
		
		Optional<Task> op =  repository.findById(id);
		
		if(op.equals(Optional.empty())) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Task>(op.get(), HttpStatus.OK);
	}
	
	
	@PostMapping("/{description}")
	public ResponseEntity<String> addTask(@PathVariable String description) {
		
		if(null!=description && !description.isEmpty()) {
			Task task = new Task();
			task.setState(Constants.STATE_TASK_INI);
			task.setDescription(description);
			
			repository.save(task);
			
			return new ResponseEntity<String>(Constants.MSJ_TASK_CREATE,HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>(Constants.MSJ_TASK_CANNOT_CREATE,HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable Integer id) {

		if(repository.existsById(id)) {
			repository.deleteById(id);
			return new ResponseEntity<String>(Constants.MSJ_TASK_DELETE,HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(Constants.MSJ_TASK_ID_NOT_FOUND,HttpStatus.NO_CONTENT);
	}
	
	@PutMapping()
	public ResponseEntity<String> updateTask(@Valid @RequestBody Task task) {
		
		Optional<Task> op = repository.findById(task.getIdTask());
		if(!op.equals(Optional.empty())) {
			repository.save(task);
			return new ResponseEntity<String>(Constants.MSJ_TASK_UPDATE,HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(Constants.MSJ_TASK_ID_NOT_FOUND,HttpStatus.NO_CONTENT);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> endTask(@PathVariable Integer id) {
		
		Optional<Task> op = repository.findById(id);
		
		if(!op.equals(Optional.empty())) {
			Task task = op.get();
			task.setState(Constants.STATE_TASK_FIN);
			repository.save(task);
			
			return new ResponseEntity<String>(Constants.MSJ_TASK_END,HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(Constants.MSJ_TASK_ID_NOT_FOUND,HttpStatus.NO_CONTENT);
	}
}
