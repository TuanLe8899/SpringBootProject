package com.complaint.management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NotFoundException.class)
	public String returnPageNotFound404(Exception exception) {
		exception.printStackTrace();
		return "error";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String returnPageError500(Exception exception) {
//		exception.printStackTrace();
//		return "500";
//	}
}
