package com.complaint.management.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.complaint.management.entity.Department;

import lombok.Data;

@Data
public class TicketDTO {

	private int id;
	
	@NotEmpty(message = "Customer name must not be empty")
	private String customerName;
	
	@Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Invalid phone number format")
	@NotEmpty(message = "customer phone must not be empty")
	private String customerPhone;
	
	@NotEmpty(message = "Title must not be empty")
	private String title;
	
	private String content;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Date create must not exceed the current date")
	private Date dateCreate;
	
	private String contentFeedback;
	
	private boolean status;
	
	private Date dateProcess;
	
	private Department department;
	
}
