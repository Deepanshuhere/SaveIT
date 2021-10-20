package com.saveit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saveit.constants.AppConstants;
import com.saveit.entity.User;
import com.saveit.service.UserService;

@Controller
@RequestMapping(AppConstants.ADMIN_PATH)
public class AdminController 
{
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addLoggedInUser(Model model, Principal principal)
	{
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		String accountStatus = user.getAccountStatus();
		model.addAttribute(AppConstants.USER, user);
		model.addAttribute(AppConstants.ACCOUNT_STATUS, accountStatus);
	}

	@GetMapping(value = {AppConstants.PATH, AppConstants.INDEX_PATH})
	public String index(Model model)
	{
		Long totalContacts = userService.getTotalContacts();
		Long totalUser = userService.getTotalUser();
		Long verifiedUsers = userService.getVerifiedUsers();
		model.addAttribute(AppConstants.TOTAL_CONTACTS, totalContacts);
		model.addAttribute(AppConstants.TOTAL_USERS, totalUser);
		model.addAttribute(AppConstants.VERIFIED_USERS, verifiedUsers);
		return AppConstants.ADMIN_ADMIN_DASHBOARD;
	}
	
	@GetMapping(AppConstants.VIEW_USERS_PATH)
	public String getAllUsers(@PathVariable(AppConstants.PAGE) Integer page,
			   Model model)
	{		
		Pageable pageable = PageRequest.of(page, 8);
		Page<User> users = userService.getAllUsers(pageable);
		model.addAttribute(AppConstants.USERS, users);
		model.addAttribute(AppConstants.CURRENT_PAGE, page);
		model.addAttribute(AppConstants.TOTAL_PAGES, users.getTotalPages());
		
		return AppConstants.ADMIN_VIEW_USERS;
	}
	
}
