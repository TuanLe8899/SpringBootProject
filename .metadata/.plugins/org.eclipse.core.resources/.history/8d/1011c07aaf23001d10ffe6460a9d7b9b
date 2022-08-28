package com.lecongtuan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lecongtuan.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT user FROM User user WHERE user.username = ?1")
	User getUserByUserName(String username);
}
