package com.complaint.management.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.complaint.management.model.TicketDTO;
import com.complaint.management.repository.TicketRepository;

@Component
public class TicketValidator implements Validator {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public boolean supports(Class<?> clazz) {
		return TicketDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TicketDTO ticket = (TicketDTO) target;
		
		List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
	
		ticketRepository.findAll().forEach((tic) -> {
			ticketDTOs.add(mapper.map(tic, TicketDTO.class));
		});
		
		ListIterator<TicketDTO> iter = ticketDTOs.listIterator();
		
		TicketDTO tic = new TicketDTO();
		while (iter.hasNext()) {
			tic = iter.next();
			if(tic.getTitle().equals(ticket.getTitle()) && (tic.getId() != ticket.getId())) {
				errors.rejectValue("title", "unique", "Title ticket already exists");
				break;
			}
		}
	}

}
