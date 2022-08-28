package com.complaint.management.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.complaint.management.model.TicketDTO;

public interface TicketService {

	void addTicket(TicketDTO ticketDTO);
	
	void updateTicket(TicketDTO ticketDTO);
	
	void deleteTicket(int id);
	
	TicketDTO getTicket(int id);
	
	Page<TicketDTO> getAllTickets(Pageable pageable);
	
	Page<TicketDTO> searchByDateFrom(Date from, Pageable pageable);
	
	Page<TicketDTO> searchByDateTo(Date to, Pageable pageable);
	
	Page<TicketDTO> searchByDateFromTo(Date from, Date to, Pageable pageable);
	
	Page<TicketDTO> searchByCustomerPhone(String customerPhone, Pageable pageable);
	
	Page<TicketDTO> searchByDepartmentID(int id, Pageable pageable);
	
	Page<TicketDTO> searchByAll(int id, Date from, Date to, String customerPhone, Pageable pageable);
}
