package com.example.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	// 1 user peut avoir plusieurs roles et 1 role peut concerner plusieurs users
	@ManyToMany(fetch = FetchType.EAGER) // pr eager => chaq chargement d user j ai desoin de charger son role
	private Collection<AppRole> roles = new ArrayList<>();
}
