package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.AppRole;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

	public AppRole findByRoleName(String roleName);
}
