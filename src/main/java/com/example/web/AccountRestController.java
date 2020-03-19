package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Service.AccountService;
import com.example.entities.AppUser;

@RestController
public class AccountRestController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm registerForm) {
		if (!registerForm.getPassword().equals(registerForm.getRePassword()))
			throw new RuntimeException("You must confirm your password");
		AppUser appUser = accountService.findByUsername(registerForm.getUsername());
		if (appUser != null)
			throw new RuntimeException("this Username already exists");
		AppUser user = new AppUser();
		user.setUsername(registerForm.getUsername());
		user.setPassword(registerForm.getPassword());
		accountService.addUser(user);
		accountService.addRoleToUser(registerForm.getUsername(), "USER");
		return user;
	}
}
