package com.saveit.binding;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OTPFormBinding 
{
	@NotEmpty(message = "OTP cannot be empty!!")
	private String OTP;
	private String email;
}
