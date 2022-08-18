package com.complaint.management.service.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.management.entity.Ticket;
import com.complaint.management.model.TicketDTO;
import com.complaint.management.repository.TicketRepository;
import com.complaint.management.service.TicketService;

@Transactional
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public void addTicket(TicketDTO ticketDTO) {
		Ticket ticket = mapper.map(ticketDTO, Ticket.class);
		ticketRepository.save(ticket);
	}

	@Override
	public void updateTicket(TicketDTO ticketDTO) {
		Ticket ticket = ticketRepository.findById(ticketDTO.getId()).orElse(null);
		if(ticket != null) {
			mapper.map(ticketDTO, ticket);
		}
		ticketRepository.save(ticket);
	}

	@Override
	public void deleteTicket(int id) {
		Ticket ticket = ticketRepository.findById(id).orElse(null);
		if (ticket != null) {
			ticketRepository.delete(ticket);
		}
	}

	@Override
	public TicketDTO getTicket(int id) {
		Ticket ticket = ticketRepository.findById(id).orElse(null);
		if(ticket != null) {
			return mapper.map(ticket, TicketDTO.class);
		}
		return null;
	}

	@Override
	public Page<TicketDTO> getAllTickets(int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.findAll(PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDateFrom(Date from, int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.searchByDateFrom(from, PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDateTo(Date to, int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.searchByDateTo(to, PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDateFromTo(Date from, Date to, int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.searchByDateFromTo(from, to, PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByCustomerPhone(String customerPhone, int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.searchByCustomerPhone(customerPhone, PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDepartmentName(String departmentName, int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.searchByDepartmentName(departmentName, PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByAll(String departmentName, Date from, Date to, String customerPhone,
			int page, int size, Sort sort) {
		Page<Ticket> tickets = ticketRepository.searchByAll(departmentName, from, to, customerPhone, PageRequest.of(page, size, sort));
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}
	
}