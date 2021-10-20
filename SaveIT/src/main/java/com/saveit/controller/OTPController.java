package com.saveit.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saveit.binding.OTPFormBinding;
import com.saveit.constants.AppConstants;
import com.saveit.entity.User;
import com.saveit.helper.CustomMessage;
import com.saveit.service.UserService;

@Controller
@RequestMapping(AppConstants.OTP_PATH)
public class OTPController 
{
	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		model.addAttribute(AppConstants.USER, user);
	}
	
	@GetMapping(AppConstants.SEND_OTP_PATH)
	public String sendOTP(Model model) 
	{
		User user = this.getLoggedInUser(model);
		userService.sendOTPEmail(user);
		OTPFormBinding otpForm = new OTPFormBinding();
		otpForm.setEmail(user.getEmail());
		model.addAttribute(AppConstants.OTP, otpForm);
		return AppConstants.LOCKED_VERIFY_ACCOUNT;
	}
	
	private User getLoggedInUser(Model model)
	{
		return (User) model.getAttribute(AppConstants.USER);
	}
	
	@GetMapping(AppConstants.RESEND_OTP_PATH)
	public String resendOTP(Model model)
	{
		User user = this.getLoggedInUser(model);
		OTPFormBinding otpForm = new OTPFormBinding();
		CustomMessage message = new CustomMessage();
		userService.sendOTPEmail(user);
		otpForm.setEmail(user.getEmail());
		model.addAttribute(AppConstants.OTP, otpForm);
		message.setContent(AppConstants.OTP_RESEND_MSG);
		message.setType(AppConstants.ALERT_SUCCESS);
		return AppConstants.LOCKED_VERIFY_ACCOUNT;
	}

	@PostMapping(AppConstants.UNLOCK_ACCOUNT_PATH)
	public String verifyEmail(@Valid @ModelAttribute(AppConstants.OTP) OTPFormBinding otpFormData, 
							  BindingResult result,
							  Model model, 
							  RedirectAttributes redirectAttributes) 
	{
		if (result.hasErrors()) 
		{
			model.addAttribute(AppConstants.OTP, otpFormData);
			System.out.println("Validation error!!");
			return AppConstants.LOCKED_VERIFY_ACCOUNT;
		}

		String status = userService.unlockAccount(otpFormData);
		CustomMessage message = new CustomMessage();

		if (status.equals(AppConstants.SUCCESS)) 
		{
			message.setContent(AppConstants.ACCOUNT_VERIFIED_MSG);
			message.setType(AppConstants.ALERT_SUCCESS);
			model.addAttribute(AppConstants.MESSAGE, message);
			redirectAttributes.addFlashAttribute(AppConstants.MESSAGE, message);
	        return AppConstants.REDIRECT_USER_INDEX_PATH;
		}

		else if (status.equals(AppConstants.INVALID_OTP)) 
		{
			message.setContent(AppConstants.ENTER_VALID_OTP_MSG);
			message.setType(AppConstants.ALERT_DANGER);
		}

		else
		{
			message.setContent(AppConstants.EXCEPTION_MSG);
			message.setType(AppConstants.ALERT_DANGER);
		}

		model.addAttribute(AppConstants.MESSAGE, message);
		model.addAttribute(AppConstants.OTP, otpFormData);
		return AppConstants.LOCKED_VERIFY_ACCOUNT;
	}


}
