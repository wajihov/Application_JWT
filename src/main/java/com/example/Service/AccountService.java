package com.example.Service;

import com.example.entities.AppRole;
import com.example.entities.AppUser;

public interface AccountService {

	public AppUser addUser(AppUser user);

	public AppRole addRole(AppRole role);

	public void addROleToUser(String user, String role);

	public AppUser findByUsername(String userName);
}
