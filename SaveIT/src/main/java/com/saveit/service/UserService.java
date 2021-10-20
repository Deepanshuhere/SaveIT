package com.saveit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.saveit.binding.ContactFormBinding;
import com.saveit.binding.OTPFormBinding;
import com.saveit.binding.PasswordFormBinding;
import com.saveit.binding.ResetForgotPasswordFormBinding;
import com.saveit.binding.SignUpFormBinding;
import com.saveit.binding.UserProfileFormBinding;
import com.saveit.entity.Contact;
import com.saveit.entity.User;

public interface UserService 
{
	public String registerUser(SignUpFormBinding formData);
	public String updateUserProfile(UserProfileFormBinding formData, User user);
	public User getUserByEmail(String email);
	public String saveContact(User user,ContactFormBinding contact);
	public Page<Contact> getAllContacts(User user, Pageable pageable);
	public Contact getContact(Integer contactId);
	public Contact updateContact(User user, ContactFormBinding formData);
	public Boolean deleteContact(Integer contactId);
	public String unlockAccount(OTPFormBinding formData);
	public Boolean sendOTPEmail(User user);
	public String resetPassword(User loggedInUser, PasswordFormBinding formData);
	public Boolean forgotPassword(User user);
	public String resetForgotPassword(User user, ResetForgotPasswordFormBinding formData);
	public Boolean saveImage(MultipartFile file, String fileName);
	public List<Contact> searchByName(String name, User user);
	public Long getTotalUser();
	public Long getVerifiedUsers();
	public Long getTotalContacts();
	public Page<User> getAllUsers(Pageable pageable);
}
