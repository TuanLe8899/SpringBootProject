package com.complaint.management.service;

import com.complaint.management.model.MessageDTO;

public interface EmailService {
	void sendEmail(MessageDTO message);
}
