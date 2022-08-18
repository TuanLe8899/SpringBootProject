package com.complaint.management.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.complaint.management.model.TicketDTO;

public interface TicketService {

	void addTicket(TicketDTO ticketDTO);
	
	void updateTicket(TicketDTO ticketDTO);
	
	void deleteTicket(int id);
	
	TicketDTO getTicket(int id);
	
	Page<TicketDTO> getAllTickets(int page, int size, Sort sort);
	
	Page<TicketDTO> searchByDateFrom(Date from, int page, int size, Sort sort);
	
	Page<TicketDTO> searchByDateTo(Date to, int page, int size, Sort sort);
	
	Page<TicketDTO> searchByDateFromTo(Date from, Date to, int page, int size, Sort sort);
	
	Page<TicketDTO> searchByCustomerPhone(String customerPhone, int page, int size, Sort sort);
	
	Page<TicketDTO> searchByDepartmentName(String departmentName, int page, int size, Sort sort);
	
	Page<TicketDTO> searchByAll(String departmentName, Date from, Date to, String customerPhone, int page, int size, Sort sort);
}
