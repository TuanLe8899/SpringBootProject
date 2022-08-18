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

import com.complaint.management.model.DepartmentDTO;
import com.complaint.management.service.DepartmentService;

@Controller
@RequestMapping("department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping({"list/{page}/{size}/{field_sort}", "list"})
	public String list(Model model,
			@RequestParam(defaultValue = "", required = false) String search,
			@PathVariable(required = false) String page,
			@PathVariable(required = false) String size,
			@PathVariable(required = false, name="field_sort") String fieldSort) {
		
		int p = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
		int s = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
		fieldSort = StringUtils.isEmpty(fieldSort) ? "id" : fieldSort;
		
		Page<DepartmentDTO> pages = departmentService.getAllDepartments(p-1, s, Sort.by(Direction.ASC, fieldSort));
		List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
				
		try {
			if(Integer.parseInt(search) <= 0) throw new NumberFormatException();
			if(departmentService.getDepartment(Integer.parseInt(search)) == null
					&& departmentService.searchByName("%"+ search +"%", p-1, s, Sort.by(Direction.ASC, fieldSort)) == null) {
				throw new NullPointerException();
			}
			departments.add(departmentService.getDepartment(Integer.parseInt(search)));
		} catch (NumberFormatException e) {
			pages = departmentService.searchByName("%"+ search +"%", p-1, s, Sort.by(Direction.ASC, fieldSort));
			departments = pages.getContent();
		} catch (NullPointerException e) {
		}
		
		int totalPages = pages.getTotalPages();
		long totalRecords = pages.getTotalElements();
		int firstRecordPerPage = (p-1)*s+1;
		int lastRecordPerPage = (int) ((p-1)*s+s >= totalRecords ? totalRecords : (p-1)*s+s);
		
		model.addAttribute("departments", departments);
		model.addAttribute("search", search);
		model.addAttribute("p", p);
		model.addAttribute("s", s);
		model.addAttribute("field_sort", fieldSort);
		model.addAttribute("total_pages", totalPages);
		model.addAttribute("total_records", totalRecords);
		model.addAttribute("first_record", firstRecordPerPage);
		model.addAttribute("last_record", lastRecordPerPage);
		return "department/list";
	}
	
	@GetMapping("add")
	public String add(Model model) {
		DepartmentDTO department = new DepartmentDTO();
		model.addAttribute("department", department);
		return "department/add";
	}
	
	@PostMapping("add")
	public String add(@ModelAttribute DepartmentDTO department) {
		Date currentDate = new Date();
		formatter.format(currentDate);
		department.setDateCreate(currentDate);
		departmentService.addDepartment(department);
		return "redirect:/department/list";
	}
	
	@GetMapping("edit")
	public String edit(Model model, @RequestParam int id) {
		DepartmentDTO department = departmentService.getDepartment(id);
		model.addAttribute("department", department);
		return "department/edit";
	}
	
	@PostMapping("edit")
	public String edit(@ModelAttribute DepartmentDTO department,
			@RequestParam("date_create") String dateCreate,
			@RequestParam("original_date") String originalDate) throws ParseException {
		if (!dateCreate.equals("")) {
			department.setDateCreate(formatter.parse(dateCreate));
		} else {
			department.setDateCreate(formatter.parse(originalDate));
		}
		departmentService.updateDepartment(department);
		return "redirect:/department/list";
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam int id) {
		departmentService.deleteDepartment(id);
		return "redirect:/department/list";
	}
}
