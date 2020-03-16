package com.example;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dao.TaskRepository;
import com.example.entities.Tasks;

@SpringBootApplication
public class AuthentificationJwtApplication implements CommandLineRunner {

	@Autowired
	private TaskRepository taskRepository;
	
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthentificationJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of("T1", "T2", "T3").forEach(t -> {
			taskRepository.save(new Tasks(null, t));
		});

		taskRepository.findAll().forEach(t -> {
			System.out.println(t.getNameTask());
		});

	}

}
