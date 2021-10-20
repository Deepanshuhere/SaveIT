package com.saveit.binding;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PasswordFormBinding 
{
	@NotEmpty(message = "Please enter current password!!")
	private String currentPassword;
	@NotEmpty(message = "Please enter new password!!")
	private String newPassword;
}
