package com.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.RoleRepository;
import com.example.dao.UserRepository;
import com.example.entities.AppRole;
import com.example.entities.AppUser;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public AppUser addUser(AppUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public AppRole addRole(AppRole role) {
		return roleRepository.save(role);
	}

	@Override
	public void addROleToUser(String user, String role) {
		AppRole roleName = roleRepository.findByRoleName(role);
		AppUser appUser = userRepository.findByUsername(user);
		appUser.getRoles().add(roleName);
	}

	@Override
	public AppUser findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

}
