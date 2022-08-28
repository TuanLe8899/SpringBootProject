package com.complaint.management.service;

import java.util.List;

import com.complaint.management.model.UserDTO;

public interface UserService {

	void addUser(UserDTO userDto);
	
	void editUser(UserDTO userDto);
	
	void deleteUser(int id);
	
	List<UserDTO> getAllUsers();
	
	UserDTO getUser(int id);
}
