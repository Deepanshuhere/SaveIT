package com.saveit.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ContactFormBinding 
{
	private Integer id;
	
	@NotEmpty(message = "Contact name cannot be empty")
	private String contactName;

	@NotEmpty(message = "Contact work cannot be empty")
	private String work;

	@NotEmpty(message = "Email cannot be empty")
	@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email")
	private String email;
	
	@NotNull(message = "Phone number cannot be empty")
	@Size(min = 10, max = 10, message = "Enter valid Phone number")
	private String phone;

	@NotEmpty(message = "Description cannot be empty")
	private String description;	
	
	private String contactImage;
}
