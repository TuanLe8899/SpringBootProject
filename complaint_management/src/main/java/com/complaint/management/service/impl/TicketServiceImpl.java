package com.complaint.management.service.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<TicketDTO> getAllTickets(Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.findAll(pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDateFrom(Date from, Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.searchByDateFrom(from, pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDateTo(Date to, Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.searchByDateTo(to, pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDateFromTo(Date from, Date to, Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.searchByDateFromTo(from, to, pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByCustomerPhone(String customerPhone, Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.searchByCustomerPhone(customerPhone, pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByDepartmentID(int id, Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.searchByDepartmentID(id, pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}

	@Override
	public Page<TicketDTO> searchByAll(int id, Date from, Date to, String customerPhone, Pageable pageable) {
		Page<Ticket> tickets = ticketRepository.searchByAll(id, from, to, customerPhone, pageable);
		return tickets.map((ticket) -> mapper.map(ticket, TicketDTO.class));
	}
	
}
