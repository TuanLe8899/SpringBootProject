package com.complaint.management.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import com.complaint.management.model.TicketDTO;
import com.complaint.management.repository.DepartmentRepository;
import com.complaint.management.service.DepartmentService;
import com.complaint.management.service.TicketService;

@Controller
@RequestMapping("ticket")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping({"list/{page}/{size}/{field_sort}", "list"})
	public String list(Model model,
			@RequestParam(name="from", defaultValue = "", required = false) String dateFrom,
			@RequestParam(name="to", defaultValue = "", required = false) String dateTo,
			@RequestParam(name="phone",defaultValue = "", required = false) String customerPhone,
			@RequestParam(name="department",defaultValue = "", required = false) String departmentName,
			@PathVariable(required = false) String page,
			@PathVariable(required = false) String size,
			@PathVariable(required = false, name="field_sort") String fieldSort) throws ParseException {
		
		int p = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
		int s = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
		fieldSort = StringUtils.isEmpty(fieldSort) ? "id" : fieldSort;
		
		Date from = formatter.parse(dateFrom);
		Date to = formatter.parse(dateTo);
		
		Page<TicketDTO> pages = ticketService.getAllTickets(p-1, s, Sort.by(Direction.ASC, fieldSort));
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		
		int checkNumberOfSearchConditions = 0;
		if(!dateFrom.equals("")) checkNumberOfSearchConditions+=1;
		if(!dateTo.equals("")) checkNumberOfSearchConditions+=1;
		if(!customerPhone.equals("")) checkNumberOfSearchConditions+=1;
		if(!departmentName.equals("")) checkNumberOfSearchConditions+=1;
		
		if(checkNumberOfSearchConditions == 1) {
			if(!dateFrom.equals("")) {
				pages = ticketService.searchByDateFrom(from, p-1, s, Sort.by(Direction.ASC, fieldSort));
			} else if(!dateTo.equals("")) {
				pages = ticketService.searchByDateTo(to, p-1, s, Sort.by(Direction.ASC, fieldSort));
			} else if(!customerPhone.equals("")) {
				pages = ticketService.searchByCustomerPhone(customerPhone, p-1, s, Sort.by(Direction.ASC, fieldSort));
			} else if(!departmentName.equals("")) {
				pages = ticketService.searchByDepartmentName(departmentName, p-1, s, Sort.by(Direction.ASC, fieldSort));
			}
		} else if(checkNumberOfSearchConditions == 2 && !(dateFrom.equals("") && dateTo.equals("")) ) {
			pages = ticketService.searchByDateFromTo(from, to, p-1, s, Sort.by(Direction.ASC, fieldSort));
		}
		
		tickets = pages.getContent();
		
//		try {
//			if(Integer.parseInt(search) <= 0) throw new NumberFormatException();
//			if(departmentService.getDepartment(Integer.parseInt(search)) == null
//					&& departmentService.searchByName("%"+ search +"%", p-1, s, Sort.by(Direction.ASC, fieldSort)) == null) {
//				throw new NullPointerException();
//			}
//			tickets.add(ticketService.g(Integer.parseInt(search))); //edit it
//		} catch (NumberFormatException e) {
//			pages = departmentService.searchByName("%"+ search +"%", p-1, s, Sort.by(Direction.ASC, fieldSort));
//		} catch (NullPointerException e) {
//		}
		
		int totalPages = pages.getTotalPages();
		long totalRecords = pages.getTotalElements();
		int firstRecordPerPage = (p-1)*s+1;
		int lastRecordPerPage = (int) ((p-1)*s+s >= totalRecords ? totalRecords : (p-1)*s+s);
		
		model.addAttribute("tickets", tickets);
		model.addAttribute("p", p);
		model.addAttribute("s", s);
		model.addAttribute("field_sort", fieldSort);
		model.addAttribute("total_pages", totalPages);
		model.addAttribute("total_records", totalRecords);
		model.addAttribute("first_record", firstRecordPerPage);
		model.addAttribute("last_record", lastRecordPerPage);
		return "ticket/list";
	}
	
	@GetMapping("add")
	public String add(Model model) {
		TicketDTO ticket = new TicketDTO();
		model.addAttribute("ticket", ticket);
		model.addAttribute("departments", departmentRepository.findAll());
		return "ticket/add";
	}
	
	@PostMapping("add")
	public String add(@ModelAttribute TicketDTO ticket,
			@RequestParam("department_id") int departmentID) {
		Date currentDate = new Date();
		formatter.format(currentDate);
		ticket.setDateCreate(currentDate);
		ticket.setDepartment(departmentRepository.findById(departmentID).orElse(null));
		ticketService.addTicket(ticket);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("edit")
	public String edit(Model model, @RequestParam int id) {
		TicketDTO ticket = ticketService.getTicket(id);
		model.addAttribute("ticket", ticket);
		model.addAttribute("departments", departmentRepository.findAll());
		return "ticket/edit";
	}
	
	@PostMapping("edit")
	public String edit(@ModelAttribute TicketDTO ticket,
			@RequestParam("department_id") int departmentID,
			@RequestParam("date_create") String dateCreate,
			@RequestParam("original_date") String originalDate) throws ParseException {
		
		if (!dateCreate.equals("")) {
			ticket.setDateCreate(formatter.parse(dateCreate));
		} else {
			ticket.setDateCreate(formatter.parse(originalDate));
		}
		ticket.setDepartment(departmentRepository.findById(departmentID).orElse(null));
		ticketService.updateTicket(ticket);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam int id) {
		ticketService.deleteTicket(id);
		return "redirect:/ticket/list";
	}
}
