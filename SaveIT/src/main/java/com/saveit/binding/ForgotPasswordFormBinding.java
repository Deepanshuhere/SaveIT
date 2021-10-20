package com.saveit.binding;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ForgotPasswordFormBinding 
{
	@NotEmpty(message = "Email address can't be empty!!")
	public String email;
}
