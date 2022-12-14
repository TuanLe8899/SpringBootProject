package com.complaint.management.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.management.entity.Department;
import com.complaint.management.model.DepartmentDTO;
import com.complaint.management.repository.DepartmentRepository;
import com.complaint.management.service.DepartmentService;

@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public void addDepartment(DepartmentDTO departmentDTO) {
		Department department = mapper.map(departmentDTO, Department.class);
		departmentRepository.save(department);
	}

	@Override
	public void updateDepartment(DepartmentDTO departmentDTO) {
		Department department = departmentRepository.findById(departmentDTO.getId()).orElse(null);
		if (department != null) {
			mapper.map(departmentDTO, department);
			departmentRepository.save(department);
		}
	}

	@Override
	public void deleteDepartment(int id) {
		Department department = departmentRepository.findById(id).orElse(null);
		if(department != null) {
			departmentRepository.delete(department);
		}
	}

	@Override
	public DepartmentDTO getDepartment(int id) {
		Department department = departmentRepository.findById(id).orElse(null);
		if(department != null ) {
			return mapper.map(department, DepartmentDTO.class);
		}
		return null;
	}

	@Override
	public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
		Page<Department> departments = departmentRepository.findAll(pageable);
		return departments.map((department) -> mapper.map(department, DepartmentDTO.class));
	}

	@Override
	public Page<DepartmentDTO> searchByName(String name, Pageable pageable) {
		Page<Department> departments = departmentRepository.searchByName(name, pageable);
		return departments.map((department) -> mapper.map(department, DepartmentDTO.class));
	}
	
}
