package com.complaint.management.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {
	private int id;
	
	private String avatar;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Email
	private String username;
	
	@NotEmpty
	@Size(min = 8, max = 32)
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
			message = "password is not valid (at least 1 number, 1 lowercase character, 1 uppercase character)")
	private String password;
	
	@NotEmpty
	private String repeatPassword;
	
	private String gender;
	
	private String role;
	
	private boolean enable;
	
	private String favourite;
	
	private String about;
}
