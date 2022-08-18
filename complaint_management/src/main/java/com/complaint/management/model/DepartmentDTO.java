package com.complaint.management.model;

import java.util.Date;
import java.util.List;

import com.complaint.management.entity.Ticket;

import lombok.Data;

@Data
public class DepartmentDTO {

	private int id;

	private String name;

	private Date dateCreate;
	
	private List<Ticket> tickets;
}
