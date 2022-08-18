package com.complaint.management.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name="ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_phone")
	private String customerPhone;
	
	private String title;
	
	private String content;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_create")
	private Date dateCreate;
	
	@Column(name = "content_feedback")
	private String contentFeedback;
	
	private boolean status;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_process")
	private Date dateProcess;
	
	@ManyToOne
	@JoinColumn(name = "id_department")
	private Department department;
}
