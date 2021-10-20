package com.saveit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.saveit.constants.AppConstants;
import com.saveit.entity.User;
import com.saveit.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userService.getUserByEmail(username);
		
		if(user == null)
			throw new UsernameNotFoundException(AppConstants.USERNAME_NOT_FOUND_EXCEPTION_MSG);
		
		return new CustomUserDetails(user);
	}

}
