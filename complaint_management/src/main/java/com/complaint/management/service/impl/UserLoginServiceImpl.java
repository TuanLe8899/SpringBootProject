//package com.complaint.management.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.complaint.management.repository.UserRepository;
//
//
//@Service
//@Transactional
//public class UserLoginServiceImpl implements UserDetailsService {
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		com.lecongtuan.entity.User user = userRepository.getUserByUserName(username);
//		
//		if(user == null) {
//			throw new UsernameNotFoundException("not found this user");
//		}
//		
//		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority(user.getRole()));
//		
//		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
//		
//		return userDetails;
//	}
//
//}
