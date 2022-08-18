package com.complaint.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.complaint.management.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
	@Query("SELECT dpm FROM Department dpm WHERE dpm.name LIKE :s")
	Page<Department> searchByName(@Param("s") String name, Pageable pageable);
}
