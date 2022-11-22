package com.mmit.securities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mmit.model.entity.User;
import com.mmit.model.repos.UserRepo;



public class UserDetailService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = repo.findUserByEmail(email);
		if(user == null)
			throw new UsernameNotFoundException("could not found user email");
		
		log.info("role : " + user.getRole().name());
		MyUserDetail userDetail = new MyUserDetail(user);
		return userDetail;
	}
	

	

}
