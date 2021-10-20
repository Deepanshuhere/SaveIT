package com.saveit.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saveit.constants.AppConstants;
import com.saveit.entity.Contact;
import com.saveit.entity.User;
import com.saveit.service.UserService;

@RestController
@RequestMapping(AppConstants.USER_PATH)
public class SearchRestController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping(AppConstants.SEARCH_PATH)
	public ResponseEntity<?> search(@PathVariable(AppConstants.QUERY) String query, Principal principal, Model model) 
	{
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		List<Contact> contacts = userService.searchByName(query, user);
		return ResponseEntity.ok(contacts);
	}


}
