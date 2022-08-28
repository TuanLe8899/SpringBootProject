package com.complaint.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.complaint.management.model.DepartmentDTO;

public interface DepartmentService {

	void addDepartment(DepartmentDTO departmentDTO);
	
	void updateDepartment(DepartmentDTO departmentDTO);
	
	void deleteDepartment(int id);
	
	DepartmentDTO getDepartment(int id);
	
	Page<DepartmentDTO> getAllDepartments(Pageable pageable);
	
	Page<DepartmentDTO> searchByName(String name, Pageable pageable);
}
