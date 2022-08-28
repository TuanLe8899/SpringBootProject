package com.complaint.management.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.complaint.management.entity.Department;
import com.complaint.management.model.DepartmentDTO;
import com.complaint.management.repository.DepartmentRepository;
import com.complaint.management.service.DepartmentService;
import com.complaint.management.validation.DepartmentValidator;

@RestController
@RequestMapping("department/api")
@Validated
public class DepartmentAPI {
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired 
	DepartmentRepository departmentRepository;
		
	@Autowired
	DepartmentValidator departmentValidator;
	
	@GetMapping("departmentnamecontains")
	public List<Department> get1(@RequestParam String name) {
		return departmentRepository.findByNameContaining(name);
	}
	
	
	@GetMapping({"list/{page}/{size}/{field_sort}", "list"})
	public List<DepartmentDTO> list(@RequestParam(defaultValue = "", required = false) String search,
			@PathVariable(required = false) String page,
			@PathVariable(required = false) String size,
			@PathVariable(required = false, name="field_sort") String fieldSort) {
		
		int p = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
		int s = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
		fieldSort = StringUtils.isEmpty(fieldSort) ? "id" : fieldSort;
		
		Pageable paginationAndSorting = PageRequest.of(p-1, s, Sort.by(Direction.ASC, fieldSort));
		
		Page<DepartmentDTO> pages = departmentService.getAllDepartments(paginationAndSorting);
		List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
				
		try {
			if(Integer.parseInt(search) <= 0) throw new NumberFormatException();
			if(departmentService.getDepartment(Integer.parseInt(search)) == null
					&& departmentService.searchByName("%"+ search +"%", paginationAndSorting) == null) {
				throw new NullPointerException();
			}
			departments.add(departmentService.getDepartment(Integer.parseInt(search)));
		} catch (NumberFormatException e) {
			pages = departmentService.searchByName("%"+ search +"%", paginationAndSorting);
			departments = pages.getContent();
		} catch (NullPointerException e) {
		}

		return departments;
	}
	
	@GetMapping("get")
	public DepartmentDTO get(@RequestParam int id) {
		return departmentService.getDepartment(id);
	}
	
	@PostMapping("add")
	public ResponseEntity<DepartmentDTO> add(@RequestBody @Valid DepartmentDTO department) {
		department.setDateCreate(new Date());
		departmentService.addDepartment(department);
		return new ResponseEntity<DepartmentDTO>(department, HttpStatus.CREATED);
	}

	@PutMapping("edit")
	public void edit(@RequestBody @Valid DepartmentDTO department) {
		departmentService.updateDepartment(department);
	}
	
	@DeleteMapping("delete")
	public void delete(@RequestParam int id) {
		departmentService.deleteDepartment(id);
	}
	
}
