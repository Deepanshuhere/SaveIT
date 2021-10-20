package com.saveit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saveit.binding.ForgotPasswordFormBinding;
import com.saveit.binding.ResetForgotPasswordFormBinding;
import com.saveit.binding.SignUpFormBinding;
import com.saveit.constants.AppConstants;
import com.saveit.entity.User;
import com.saveit.helper.CustomMessage;
import com.saveit.service.UserService;

@Controller
public class HomeController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping(value = {AppConstants.PATH, AppConstants.HOME_PATH})
	public String home(Model model)
	{
		model.addAttribute(AppConstants.TITLE, AppConstants.HOME_TITLE);
		return AppConstants.HOME;
	}
		
	@GetMapping(value = {AppConstants.LOGIN_PATH})
	public String login(Model model)
	{
		CustomMessage message = (CustomMessage)model.asMap().get(AppConstants.CUSTOMER_EMAIL);
		if(message != null)
			model.addAttribute(AppConstants.MESSAGE, message);
		
		model.addAttribute(AppConstants.TITLE, AppConstants.LOGIN_TITLE);
		return AppConstants.LOGIN;
	}

	@GetMapping(value = {AppConstants.SIGNUP_PATH})
	public String signup(Model model)
	{
		model.addAttribute(AppConstants.TITLE, AppConstants.REGISTER_TITLE);
		model.addAttribute(AppConstants.USER, new SignUpFormBinding());
		return AppConstants.SIGNUP;
	}

	@PostMapping(value = {AppConstants.REGISTER_PATH})
	public String registerUser(@Valid @ModelAttribute(AppConstants.USER) SignUpFormBinding formData,
								BindingResult result,
								Model model)
	{
		if(result.hasErrors())
		{
			model.addAttribute(AppConstants.USER, formData);
			System.out.println("Validation error!!");
			return AppConstants.SIGNUP;
		}
		
		String status = userService.registerUser(formData);
		CustomMessage message = new CustomMessage();
		
		if(status.equals(AppConstants.SUCCESS))
		{
			message.setContent(AppConstants.ACCOUNT_REGISTERED);
			message.setType(AppConstants.ALERT_SUCCESS);
			model.addAttribute(AppConstants.MESSAGE, message);
			return AppConstants.LOGIN;
		}
		
		else if(status.equals(AppConstants.FAILURE))
			message.setContent(AppConstants.EXCEPTION_MSG);
		
		else if(status.equals(AppConstants.DUPLICATE))
		{
			message.setContent(AppConstants.DUPLICATE_EMAIL_MSG);
			model.addAttribute(AppConstants.USER, formData);
		}

		message.setType(AppConstants.ALERT_DANGER);
		model.addAttribute(AppConstants.MESSAGE, message);
		return AppConstants.SIGNUP;
	}
			
	@GetMapping(AppConstants.FORGOT_PASSWORD_FORM_PATH)
	public String forgotPassword(Model model)
	{
		CustomMessage message = (CustomMessage)model.asMap().get(AppConstants.CUSTOMER_EMAIL);
		if(message != null)
			model.addAttribute(AppConstants.MESSAGE, message);
		ForgotPasswordFormBinding forgotForm = new ForgotPasswordFormBinding();
		model.addAttribute(AppConstants.FORGOT, forgotForm);
		return AppConstants.FORGOT_PASSWORD;
	}
	
	@PostMapping(AppConstants.FORGOT_PASSWORD_EMAIL_PATH)
	public String sendForgotPasswordMail(@Valid @ModelAttribute(AppConstants.FORGOT) ForgotPasswordFormBinding formData,
										 BindingResult result,
										 Model model, 
										 RedirectAttributes redirectAttributes)
	{
		if(result.hasErrors())
		{
			model.addAttribute(AppConstants.FORGOT, formData);
			System.out.println("Validation error!!");
			return AppConstants.FORGOT_PASSWORD;
		}
		
		User user = userService.getUserByEmail(formData.getEmail());
		CustomMessage message = new CustomMessage();
		
		if(user == null)
		{
			message.setContent(AppConstants.UNREGISTERED_EMAIL_MSG);
			message.setContent(AppConstants.ALERT_DANGER);
			model.addAttribute(AppConstants.MESSAGE, message);
			redirectAttributes.addFlashAttribute(AppConstants.MESSAGE, message);
			return AppConstants.REDIRECT_FORGOT_PASSWORD_FORM_PATH;
		}
		
		userService.forgotPassword(user);
		message.setContent(AppConstants.RESET_EMAIL_MSG);
		message.setType(AppConstants.ALERT_SUCCESS);
		model.addAttribute(AppConstants.MESSAGE, message);
		redirectAttributes.addFlashAttribute(AppConstants.MESSAGE, message);
		return AppConstants.LOGIN_PATH;
	}
	
	@GetMapping(AppConstants.NEW_PASSWORD_PATH)
	public String resetPasswordForm(@PathVariable(AppConstants.EMAIL) String email, Model model)
	{
		ResetForgotPasswordFormBinding resetForm = new ResetForgotPasswordFormBinding();
		resetForm.setEmail(email);
		model.addAttribute(AppConstants.RESET_FORM, resetForm);
		return AppConstants.RESET_PASSWORD;
	}
	
	@PostMapping(AppConstants.PROCESS_RESET_PASSWORD_PATH)
	public String setNewPassword(@Valid @ModelAttribute(AppConstants.RESET_FORM) ResetForgotPasswordFormBinding formData,
								 BindingResult result,    
								 Model model)
	{
		if(result.hasErrors())
		{
			model.addAttribute(AppConstants.RESET_FORM, formData);
			System.out.println("Validation error!!");
			return AppConstants.RESET_PASSWORD;
		}
		
		User user = (User) userService.getUserByEmail(formData.getEmail());
		userService.resetForgotPassword(user, formData);
		
		CustomMessage message = new CustomMessage();
		message.setContent(AppConstants.PASSWORD_UPDATED_MSG);
		message.setType(AppConstants.ALERT_SUCCESS);
		model.addAttribute(AppConstants.MESSAGE, message);
		return AppConstants.LOGIN_PATH;
	}
	
}
