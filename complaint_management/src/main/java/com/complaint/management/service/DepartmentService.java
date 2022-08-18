package com.complaint.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.complaint.management.model.DepartmentDTO;

public interface DepartmentService {

	void addDepartment(DepartmentDTO departmentDTO);
	
	void updateDepartment(DepartmentDTO departmentDTO);
	
	void deleteDepartment(int id);
	
	DepartmentDTO getDepartment(int id);
	
	Page<DepartmentDTO> getAllDepartments(int page, int size, Sort sort);
	
	Page<DepartmentDTO> searchByName(String name, int page, int size, Sort sort);
}