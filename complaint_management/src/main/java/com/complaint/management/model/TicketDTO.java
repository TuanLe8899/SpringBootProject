package com.complaint.management.model;

import java.util.Date;

import com.complaint.management.entity.Department;

import lombok.Data;

@Data
public class TicketDTO {

	private int id;
	
	private String customerName;
	
	private String customerPhone;
	
	private String title;
	
	private String content;
	
	private Date dateCreate;
	
	private String contentFeedback;
	
	private boolean status;
	
	private Date dateProcess;
	
	private Department department;
	
}
