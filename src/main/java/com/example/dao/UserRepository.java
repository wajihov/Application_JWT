package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

	public AppUser findByUsername(String username);
}
