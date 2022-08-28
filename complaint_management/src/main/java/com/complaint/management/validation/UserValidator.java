package com.complaint.management.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.complaint.management.entity.User;


@Component
public class UserValidator {
	
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if(user.getName().equals(null) || user.getName().length() == 0) {
			errors.rejectValue("name", "field.empty");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.empty");
		
		if(!user.getUsername().contains("@gmail.com")) {
			errors.rejectValue("username", "field.username.invalid");
		}
		
		if(user.getPassword().length() < 6 || user.getPassword().length() > 12) {
			errors.rejectValue("password", "field.password.length");
		}
	}
	
}
