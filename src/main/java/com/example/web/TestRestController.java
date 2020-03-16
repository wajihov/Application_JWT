package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.TaskRepository;
import com.example.entities.Tasks;

@RestController
public class TestRestController {

	@Autowired
	private TaskRepository taskRepository;

	@GetMapping("/tasks")
	public List<Tasks> tasks() {
		return taskRepository.findAll();
	}

	@PostMapping("/tasks")
	public Tasks AddTask(@RequestBody Tasks tasks) {
		return taskRepository.save(tasks);
	}

}
