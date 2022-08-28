package com.complaint.management.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.management.entity.User;
import com.complaint.management.model.UserDTO;
import com.complaint.management.repository.UserRepository;
import com.complaint.management.service.UserService;


@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public void addUser(UserDTO userDto) {
		User user = modelMapper.map(userDto, User.class);
		
		userRepository.save(user);
		userDto.setId(user.getId());
		
	}

	@Override
	public void editUser(UserDTO userDto) {
		User user = userRepository.findById(userDto.getId()).orElse(null);
		if(user != null) {
			modelMapper.map(userDto, user);
			userRepository.save(user);
		}
		
	}

	@Override
	public void deleteUser(int id) {
		User user = userRepository.findById(id).orElse(null);
		if(user != null) {
			userRepository.delete(user);
		}	
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		
		userRepository.findAll().forEach((user) -> {
			userDTOs.add(modelMapper.map(user, UserDTO.class));
		});
		
		return userDTOs;
	}

	@Override
	public UserDTO getUser(int id) {
		User user = userRepository.findById(id).orElse(null);
		
		if (user != null) {
			return modelMapper.map(user, UserDTO.class);
		}
		
		return null;
	}

}
