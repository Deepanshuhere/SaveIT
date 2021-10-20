package com.saveit.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalTime;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saveit.binding.ContactFormBinding;
import com.saveit.binding.PasswordFormBinding;
import com.saveit.binding.UserProfileFormBinding;
import com.saveit.constants.AppConstants;
import com.saveit.entity.Contact;
import com.saveit.entity.User;
import com.saveit.helper.CustomMessage;
import com.saveit.service.UserService;

@Controller
@RequestMapping(AppConstants.USER_PATH)
public class UserController 
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
	
	private Boolean checkAccountStatus(Model model)
	{
		String accountStatus = (String)model.getAttribute(AppConstants.ACCOUNT_STATUS);
		Boolean flag = true;
		
		if(accountStatus.equals(AppConstants.UNLOCKED))
			flag = false;
		
		return flag;
	}
	
	@GetMapping(AppConstants.LOCKED_PATH)
	public String lockedDashboard(Model model)
	{
		if(!checkAccountStatus(model))
 			return AppConstants.REDIRECT_USER_INDEX_PATH;
		return AppConstants.LOCKED_LOCKED_DASHBOARD;
	}
	
	@RequestMapping(AppConstants.INDEX_PATH)
	public String dashboard(Model model)
	{
		if(checkAccountStatus(model))
 			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		
		CustomMessage message = (CustomMessage)model.asMap().get(AppConstants.CUSTOMER_EMAIL);

		if(message != null)
			model.addAttribute(AppConstants.MESSAGE, message);

		return AppConstants.NORMAL_USER_DASHBOARD;
	}
	
	@GetMapping(AppConstants.ADD_CONTACT_PATH)
	public String addContactForm(Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		model.addAttribute(AppConstants.TITLE, AppConstants.ADD_CONTACT_TITLE);
		model.addAttribute(AppConstants.CONTACT, new ContactFormBinding());
		return AppConstants.NORMAL_ADD_CONTACT;
	}
	
	@PostMapping(AppConstants.PROCESS_CONTACT_PATH)
	public String processSaveContact(@Valid @ModelAttribute(AppConstants.CONTACT) ContactFormBinding formData,
    								 BindingResult result,
									 @RequestParam(AppConstants.PROFILE_IMAGE) MultipartFile file,
									 Model model) throws IOException
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;

		if(result.hasErrors())
		{
			model.addAttribute(AppConstants.CONTACT, formData);
			System.out.println("Validation error!!");
			return AppConstants.NORMAL_ADD_CONTACT;
		}
		
		if(!file.isEmpty())
		{
			String fileName = formData.getContactName() + LocalTime.now().toString() + file.getOriginalFilename();
			userService.saveImage(file, fileName);
			formData.setContactImage(fileName);
		}
				
		User loggedInUser = (User) model.getAttribute(AppConstants.USER);
		String status = userService.saveContact(loggedInUser, formData);
		CustomMessage message = new CustomMessage();

		if(status.equals(AppConstants.SUCCESS))
		{
			message.setContent(AppConstants.CONTACT_SAVED_MSG);
			message.setType(AppConstants.ALERT_SUCCESS);
		}
		
		else if(status.equals(AppConstants.FAILURE))
		{
			message.setContent(AppConstants.EXCEPTION_MSG);
			message.setType(AppConstants.ALERT_DANGER);
		}
		
		model.addAttribute(AppConstants.MESSAGE,message);
		model.addAttribute(AppConstants.CONTACT, new ContactFormBinding());
		return AppConstants.NORMAL_ADD_CONTACT;
	}
	
	@GetMapping(AppConstants.VIEW_CONTACTS_PAGE_PATH)
	public String viewContacts(@PathVariable(AppConstants.PAGE) Integer page,
							   Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		User loggedInUser = (User) model.getAttribute(AppConstants.USER);
		
		//page --> Current page
		//5 --> Contact per page
		Pageable pageable = PageRequest.of(page, 7);
		Page<Contact> contacts = userService.getAllContacts(loggedInUser, pageable);
		
		model.addAttribute(AppConstants.CONTACTS, contacts);
		model.addAttribute(AppConstants.CURRENT_PAGE, page);
		model.addAttribute(AppConstants.TOTAL_PAGES, contacts.getTotalPages());
		return AppConstants.NORMAL_VIEW_CONTACTS;
	}
	
	@GetMapping(AppConstants.CONTACT_CONTACT_ID_PATH)
	public String getContact(@PathVariable(AppConstants.CONTACT_ID) Integer contactId,
							 Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		Contact contact = userService.getContact(contactId);
		User loggedInUser = (User) model.getAttribute(AppConstants.USER);
		
		if(loggedInUser.getId() == contact.getUser().getId())
			model.addAttribute(AppConstants.CONTACT, contact);

		model.addAttribute(AppConstants.TITLE, AppConstants.CONTACT_PROFILE_TITLE);
		return AppConstants.NORMAL_VIEW_CONTACT;
	}

	@GetMapping(AppConstants.UPDATE_CONTACT_FORM_PATH)
	public String updateContactForm(@PathVariable(AppConstants.CONTACT_ID) Integer contactId,
									Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		Contact contact = userService.getContact(contactId);
		ContactFormBinding contactForm = new ContactFormBinding();
		BeanUtils.copyProperties(contact, contactForm);
		contactForm.setPhone(String.valueOf(contact.getPhone()));
		model.addAttribute(AppConstants.CONTACT, contactForm);
		return AppConstants.NORMAL_UPDATE_CONTACT;
	}
	
	@PostMapping(AppConstants.UPDATE_CONTACT_PATH)
	public String updateContact(@Valid @ModelAttribute(AppConstants.CONTACT) ContactFormBinding formData,
								BindingResult result,
								@RequestParam(AppConstants.PROFILE_IMAGE) MultipartFile file,
								Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;

		if(result.hasErrors())
		{
			model.addAttribute(AppConstants.CONTACT, formData);
			System.out.println("Validation error Update Contact!!");
			return AppConstants.NORMAL_UPDATE_CONTACT;
		}
		
		if(!file.isEmpty())
		{
			String fileName = formData.getContactName() + LocalTime.now().toString() + file.getOriginalFilename();
			userService.saveImage(file, fileName);
			formData.setContactImage(fileName);
		}

		User loggedInUser = (User) model.getAttribute(AppConstants.USER);
		Contact updatedContact = userService.updateContact(loggedInUser, formData);
		CustomMessage message = new CustomMessage();
		message.setContent(AppConstants.RECORD_UPDATED_MSG);
		message.setType(AppConstants.ALERT_SUCCESS);
		model.addAttribute(AppConstants.MESSAGE, message);
		model.addAttribute(AppConstants.CONTACT, updatedContact);
		return AppConstants.NORMAL_VIEW_CONTACT;
	}
	
	
	@GetMapping(AppConstants.DELETE_CONTACT_PATH)
	public String deleteContact(@PathVariable(AppConstants.CONTACT_ID) Integer contactId,
								Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		Contact contact = userService.getContact(contactId);
		User loggedInUser = (User) model.getAttribute(AppConstants.USER);
		Boolean status = false;
		
		if(loggedInUser.getId() == contact.getUser().getId())
			status = userService.deleteContact(contactId);
	
		CustomMessage message = new CustomMessage();
		
		if(status)
		{
			message.setContent(AppConstants.CONTACT_DELETED_MSG);
			message.setType(AppConstants.ALERT_SUCCESS);
		}
		
		else
		{
			message.setContent(AppConstants.EXCEPTION_MSG);
			message.setType(AppConstants.ALERT_DANGER);
		}

		model.addAttribute(AppConstants.MESSAGE, message);
		return AppConstants.REDIRECT_USER_VIEW_CONTACTS_PATH;
	}
	
	@GetMapping(AppConstants.USER_PROFILE_PATH)
	public String getUserProfile(Model model)
	{
		if(checkAccountStatus(model))
			return AppConstants.REDIRECT_USER_LOCKED_PATH;
		User loggedInUser = (User) model.getAttribute(AppConstants.USER);
		CustomMessage message = (CustomMessage)model.asMap().get(AppConstants.CUSTOMER_EMAIL);

		if(message != null)
			model.addAttribute(AppConstants.MESSAGE, message);

		model.addAttribute(AppConstants.TITLE, loggedInUser.getName());
		return AppConstants.NORMAL_USER_PROFILE;
	}

	@GetMapping(AppConstants.UPDATE_PROFILE_FORM_PATH)
	public String updateProfileForm(Model model)
	{
		User loggedInUser = (User)model.getAttribute(AppConstants.USER);
		UserProfileFormBinding profile = new UserProfileFormBinding();
		BeanUtils.copyProperties(loggedInUser, profile);
		profile.setPhone(String.valueOf(loggedInUser.getPhone()));
		model.addAttribute(AppConstants.PROFILE, profile);
		return AppConstants.NORMAL_UPDATE_PROFILE;
	}
	
	@PostMapping(AppConstants.PROCESS_UPDATE_PROFILE_PATH)
	public String updateProfile(@Valid @ModelAttribute(AppConstants.PROFILE) UserProfileFormBinding formData,
								BindingResult result,
								@RequestParam(AppConstants.PROFILE_IMAGE) MultipartFile file,
								Model model,
								RedirectAttributes redirectAttributes)
	{
		if(result.hasErrors())
		{
			model.addAttribute(AppConstants.PROFILE, formData);
			System.out.println("Validation error Update Profile!!");
			return AppConstants.NORMAL_UPDATE_PROFILE;
		}
		
		if(!file.isEmpty())
		{
			String fileName = formData.getName() + LocalTime.now().toString() + file.getOriginalFilename();
			userService.saveImage(file, fileName);
			formData.setContactImage(fileName);
		}

		User user = (User)model.getAttribute(AppConstants.USER);
		String status = userService.updateUserProfile(formData, user);
		CustomMessage message = new CustomMessage();

		if(status.equals(AppConstants.SUCCESS))
		{
			message.setContent(AppConstants.PROFILE_UPDATED_MSG);
			message.setType(AppConstants.ALERT_SUCCESS);
		}
		
		else if(status.equals(AppConstants.FAILURE))
		{
			message.setContent(AppConstants.EXCEPTION_MSG);
			message.setType(AppConstants.ALERT_DANGER);
		}
		
		model.addAttribute(AppConstants.MESSAGE,message);
		redirectAttributes.addFlashAttribute(AppConstants.MESSAGE, message);
		return AppConstants.REDIRECT_USER_USER_PROFILE_PATH;
	}
	
	@GetMapping(AppConstants.SECURITY_PATH)
	public String userSecurity()
	{
		return AppConstants.NORMAL_SECURITY;
	}
	
	@GetMapping(AppConstants.UPDATE_PASSWORD_FORM_PATH)
	public String updatePasswordForm(Model model)
	{
		PasswordFormBinding resetPassword = new PasswordFormBinding();
		model.addAttribute(AppConstants.RESET_PASSWORD_TEXT, resetPassword);
		return AppConstants.NORMAL_UPDATE_PASSWORD;
	}
	
	@PostMapping(AppConstants.PROCESS_UPDATE_PASSWORD_PATH)
	public String processUpdatePassword(@Valid @ModelAttribute(AppConstants.RESET_PASSWORD_TEXT) PasswordFormBinding formData, 
										BindingResult result, 
										Model model)
	{
		if(result.hasErrors()) 
		{
			model.addAttribute(AppConstants.RESET_PASSWORD_TEXT, formData);
			System.out.println("Validation error Update Password!!");
			return AppConstants.NORMAL_UPDATE_PASSWORD;
		}
		
		User loggedInUser = (User)model.getAttribute(AppConstants.USER);
		CustomMessage message = new CustomMessage();
		
		String status = userService.resetPassword(loggedInUser, formData);
		
		if(status.equals(AppConstants.SUCCESS))
		{
			message.setContent(AppConstants.PASSWORD_UPDATED_MSG);
			message.setType(AppConstants.ALERT_SUCCESS);
		}
		
		else if(status.equals(AppConstants.INCORRECT))
		{
			message.setContent(AppConstants.ENTER_CORRECT_PASSWORD_MSG);
			message.setType(AppConstants.ALERT_DANGER);
			model.addAttribute(AppConstants.MESSAGE, message);
			return AppConstants.NORMAL_UPDATE_PASSWORD;
		}
		
		else if(status.equals(AppConstants.SAME))
		{
			message.setContent(AppConstants.SAME_OLD_PASSWORD_MSG);
			message.setType(AppConstants.ALERT_DANGER);
			model.addAttribute(AppConstants.MESSAGE, message);
			return AppConstants.NORMAL_UPDATE_PASSWORD;
		}
		
		else if(status.equals(AppConstants.FAILURE))
		{
			message.setContent(AppConstants.EXCEPTION_MSG);
			message.setType(AppConstants.ALERT_DANGER);
		}
		
		
		model.addAttribute(AppConstants.MESSAGE, message);
		return AppConstants.NORMAL_SECURITY;
	}	
	
	
}
