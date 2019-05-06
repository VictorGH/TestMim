package com.mimacom.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.mimacom.task.constants.Constants;

@Entity
@Table(name = "task")
public class Task  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTask;
	
	@NotEmpty(message=Constants.MSJ_VALIDATE_DESCRIPTION)
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Pattern(regexp = Constants.REGESEXP_VALIDATE_STATE)
	@NotEmpty(message=Constants.MSJ_VALIDATE_STATE)
	@Column(name = "STATE", nullable = false)
	private String  state;
	

	public Integer getIdTask() {
		return idTask;
	}
	public void setIdTask(Integer idTask) {
		this.idTask = idTask;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
