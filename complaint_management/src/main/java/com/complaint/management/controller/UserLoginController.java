package com.complaint.management.controller;

import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.complaint.management.entity.User;
import com.complaint.management.model.MessageDTO;
import com.complaint.management.model.UserDTO;
import com.complaint.management.repository.UserRepository;
import com.complaint.management.service.EmailService;
import com.complaint.management.service.UserService;

@Controller
public class UserLoginController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("register")
	public String register(Model model) {
		UserDTO user = new UserDTO();
		model.addAttribute("user", user);
		return "register";
	}
	
	@PostMapping("register")
	public String register(@ModelAttribute("user") @Valid UserDTO user,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		if(user.getPassword().equals(user.getRepeatPassword())) {
			user.setEnable(true);
			user.setRole("ROLE_ADMIN");
			userService.addUser(user);
			return "redirect:/department/list";
		}
		return "";
	}
	
	@GetMapping("forgot-password")
	public String forgotPassword() {
		return "forgot-password";
	}
	
	@PostMapping("forgot-password")
	public String forgotPassword(Model model,
			@RequestParam("email") String email) {
		ListIterator<UserDTO> iter = userService.getAllUsers().listIterator();
		while(iter.hasNext()) {
			UserDTO userCheck = iter.next();
			if(email.equals(userCheck.getUsername())) {
				int length = 6;
				boolean useLetters = true;
				boolean useNumbers = true;
				String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
				
				userCheck.setPassword(randomPassword);
				userService.editUser(userCheck);
				
				MessageDTO message = new MessageDTO();
				message.setFrom("thedog9009@gmail.com");
				message.setTo(email);
				message.setSubject("Get your new password");
				message.setContent("Your new password: " + randomPassword);
				emailService.sendEmail(message);
				
				return "redirect:/reset-password?username=" + email;
			}
		}
		model.addAttribute("error", "Email does not exists!");
		return "forgot-password";
	}
	
	@GetMapping("reset-password")
	public String resetPassword(@RequestParam("username") String username,
			Model model) {
		User user = userRepository.findUserByUsername(username);
		UserDTO userDto = mapper.map(user, UserDTO.class);
		model.addAttribute("user", userDto);
		return "reset-password";
	}
	
	@PostMapping("reset-password")
	public String resetPassword(HttpServletRequest request,
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("currentPasswordForCompare") String currentPasswordForCompare,
			@ModelAttribute("user") @Valid UserDTO user,
			BindingResult bindingResult) {
		if(!currentPassword.equals(currentPasswordForCompare)) {
			request.setAttribute("error", "current password is not correct");
			return "reset-password";
		}
		if (bindingResult.hasErrors()) {
			return "reset-password";
		}
		if(user.getPassword().equals(user.getRepeatPassword())) {
			userService.editUser(user);
			return "redirect:/department/list";
		}
		return "reset-password";
	}
	
}
