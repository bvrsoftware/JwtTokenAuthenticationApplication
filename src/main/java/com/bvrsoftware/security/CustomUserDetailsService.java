package com.bvrsoftware.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bvrsoftware.model.User;
import com.bvrsoftware.repositry.UserRepositry;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepositry repositry;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u=repositry.findByEmail(username);
		if(u==null)
			throw new UsernameNotFoundException("NO USER");
		
		return new CustomUserDetails(u);
	}

}
