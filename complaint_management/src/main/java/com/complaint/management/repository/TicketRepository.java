package com.complaint.management.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.complaint.management.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Query("SELECT tk FROM Ticket tk WHERE tk.dateCreate >= :d")
	Page<Ticket> searchByDateFrom(@Param("d") Date from, Pageable pageable);
	
	@Query("SELECT tk FROM Ticket tk WHERE tk.dateCreate <= :d")
	Page<Ticket> searchByDateTo(@Param("d") Date to, Pageable pageable);
	
	@Query("SELECT tk FROM Ticket tk WHERE tk.dateCreate BETWEEN :d AND :d2")
	Page<Ticket> searchByDateFromTo(@Param("d") Date from, @Param("d2") Date to, Pageable pageable);
	
	Page<Ticket> searchByCustomerPhone(String customerPhone, Pageable pageable);
	
	@Query("SELECT tk FROM Ticket tk JOIN tk.department dpm WHERE dpm.name LIKE :s")
	Page<Ticket> searchByDepartmentName(@Param("s") String departmentName, Pageable pageable);
	
	@Query("SELECT tk FROM Ticket tk JOIN tk.department dpm "
			+ "WHERE dpm.name LIKE :s AND tk.dateCreate >= :d AND tk.dateCreate <= :d2 "
			+ "AND tk.customerPhone = :s2")
	Page<Ticket> searchByAll(@Param("s") String departmentName,
			@Param("d") Date from,
			@Param("d2") Date to,
			@Param("s2") String customerPhone,
			Pageable pageable);
}
