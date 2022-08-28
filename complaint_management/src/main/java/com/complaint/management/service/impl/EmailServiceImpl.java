package com.complaint.management.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.complaint.management.model.MessageDTO;
import com.complaint.management.service.EmailService;

@Transactional
@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void sendEmail(MessageDTO message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(); 
		mailMessage.setFrom(message.getFrom());
		mailMessage.setTo(message.getTo()); 
		mailMessage.setSubject(message.getSubject()); 
		mailMessage.setText(message.getContent());
		javaMailSender.send(mailMessage);
	}

}
