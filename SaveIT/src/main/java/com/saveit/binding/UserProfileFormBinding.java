package com.saveit.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserProfileFormBinding 
{
	@NotEmpty(message = "User name cannot be empty")
	private String name;
	
	@NotEmpty(message = "Please tell us something about you")
	private String about;

	@NotNull(message = "Phone number cannot be empty")
	@Size(min = 10, max = 10, message = "Enter valid Phone number")
	private String phone;
	
	private String contactImage;
}
