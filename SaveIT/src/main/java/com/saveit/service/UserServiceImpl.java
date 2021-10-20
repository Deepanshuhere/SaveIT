package com.saveit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saveit.binding.ContactFormBinding;
import com.saveit.binding.OTPFormBinding;
import com.saveit.binding.PasswordFormBinding;
import com.saveit.binding.ResetForgotPasswordFormBinding;
import com.saveit.binding.SignUpFormBinding;
import com.saveit.binding.UserProfileFormBinding;
import com.saveit.constants.AppConstants;
import com.saveit.entity.Contact;
import com.saveit.entity.User;
import com.saveit.repository.ContactRepository;
import com.saveit.repository.UserRepository;
import com.saveit.util.EmailUtils;

@Service
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailUtils emailUtils;
 	
	@Override
	public String registerUser(SignUpFormBinding formData) 
	{
		User savedRecord = null;
		User user = new User();
		try
		{
			BeanUtils.copyProperties(formData, user);
			user.setPhone(Long.parseLong(formData.getPhone()));
			user.setRole(AppConstants.ROLE_USER);
			user.setPassword(passwordEncoder.encode(formData.getPassword()));
			user.setAccountStatus(AppConstants.LOCKED);
			savedRecord = userRepo.save(user);
		}
		
		catch(Exception e)
		{
			System.out.println("User Registration Exception " + e);
		}
			
		if(savedRecord.getId() != null)
			return AppConstants.SUCCESS;
		
		return AppConstants.FAILURE;
	} 
	
	@Override
	public String updateUserProfile(UserProfileFormBinding formData, User loggedInUser)
	{
		User savedRecord = null;
		try
		{
			BeanUtils.copyProperties(formData, loggedInUser);
			savedRecord = userRepo.save(loggedInUser);
		}
		
		catch (Exception e) 
		{
			System.out.println("Update Profile Exception " + e);
		}	
			
		if(savedRecord.getId() != null)
			return AppConstants.SUCCESS;		

		return AppConstants.FAILURE;
	}
	 
	@Override
	public Boolean sendOTPEmail(User user)
	{
		Boolean status = false;
		try
		{
			String OTP = this.generateOTP(7);
			System.out.println("OTP ==> " + OTP);
			String encodedOTP = passwordEncoder.encode(OTP);
			user.setOTP(encodedOTP);
			userRepo.save(user);
			String subject = AppConstants.OTP_MAIL_SUBJECT_MSG + user.getName();
			StringBuilder builder = this.getMailBody(AppConstants.UNLOCK_MAIL_BODY_FILE);
			String body = builder.toString().replace(AppConstants.REPLACE_NAME, user.getName())
											.replace(AppConstants.REPLACE_OTP, OTP);
			status = emailUtils.sendEmail(user.getEmail(), subject, body);
		}	
		
		catch(Exception e)
		{
			System.out.println("Sending OTP Exception " + e);
		}
		
		return status;
	}	

	@Override
	public Boolean forgotPassword(User user)
	{
		Boolean status = false;
		try
		{
			String subject = AppConstants.RESET_PASSWORD_SUBJECT_MSG + user.getEmail();
			StringBuilder builder = this.getMailBody(AppConstants.FORGOT_PASSWORD_MAIL_BODY_FILE);
			String body = builder.toString().replace(AppConstants.REPLACE_NAME, user.getName())
											.replace(AppConstants.REPLACE_EMAIL, user.getEmail());
			status = emailUtils.sendEmail(user.getEmail(), subject, body);
		}
		
		catch(Exception e)
		{
			System.out.println("Forgot Password Exception " + e);
		}
		return status;
	}
	
	private StringBuilder getMailBody(String fileName)
	{
		StringBuilder sb = new StringBuilder(AppConstants.EMPTY_STRING);

		try(FileReader file = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(file))
		{
			String line = buffer.readLine();
			while (line != null) 
			{
				sb.append(line);
				line = buffer.readLine();
			}
		}
		
		catch(Exception e)
		{
			System.out.println("Get Mail Body Exception " + e);
		}
		return sb;
	}

	private String generateOTP(int length) 
	{
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) 
			sb.append(AppConstants.RANDOM_STRING.charAt(random.nextInt(AppConstants.RANDOM_STRING.length())));

		return sb.toString();
	}


	@Override
	public User getUserByEmail(String email) 
	{
		return userRepo.findByEmail(email);
	}

	@Override
	public String saveContact(User user, ContactFormBinding formData) 
	{
		try
		{
			Contact contact = new Contact();
			BeanUtils.copyProperties(formData, contact);
			contact.setUser(user);
			contact.setPhone(Long.parseLong(formData.getPhone()));
			user.getContacts().add(contact);
			User status = userRepo.save(user);
			if(status != null)
				return AppConstants.SUCCESS;
		}
		
		catch(Exception e)
		{
			System.out.println("Save Contact Exception " + e);
			return AppConstants.FAILURE;
		}
		return AppConstants.FAILURE;
	}

	@Override
	public Page<Contact> getAllContacts(User user, Pageable pageable) 
	{
		return contactRepo.findByUser(user, pageable);
	}

	@Override
	public Contact getContact(Integer contactId) 
	{
		return contactRepo.findById(contactId).get();
	}

	@Override
	public Contact updateContact(User user,ContactFormBinding formData) 
	{
		Contact contact = new Contact();
		Contact savedContact = null;
		try
		{
			BeanUtils.copyProperties(formData, contact);
			contact.setPhone(Long.parseLong(formData.getPhone()));
			contact.setUser(user);
			savedContact = contactRepo.save(contact);
		}
		
		catch(Exception e)
		{
			System.out.println("Update Contact Exception " + e);
		}
			
		return savedContact;
	}

	@Override
	public Boolean deleteContact(Integer contactId) 
	{
		try
		{
			contactRepo.deleteById(contactId);
		}
		
		catch(Exception e)
		{
			System.out.println("Delete Contact Exception " + e);
			return false;
		}
			
		return true;
	}

	@Override
	public String unlockAccount(OTPFormBinding formData) 
	{
		try
		{
			String userEmail = formData.getEmail();
			String OTP = formData.getOTP();
			User user = userRepo.findByEmail(userEmail);
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
			Boolean status = encoder.matches(OTP, user.getOTP());  
			
			if(status)
			{
				user.setAccountStatus(AppConstants.UNLOCKED);
				user.setOTP(null);
				userRepo.save(user);
				return AppConstants.SUCCESS;
			}
			
			else
				return AppConstants.INVALID_OTP;
		}
		
		catch(Exception e)
		{
			System.out.println("Unlock Account Exception " + e);
		}
		return AppConstants.FAILED;
	}

	@Override
	public String resetPassword(User loggedInUser, PasswordFormBinding formData) 
	{
		try
		{
			String currentPassword = formData.getCurrentPassword();
			String newPassword = formData.getNewPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
			Boolean status = encoder.matches(currentPassword, loggedInUser.getPassword());  
	
			if(!status)
				return AppConstants.INCORRECT;
			
			if(currentPassword.equals(newPassword))
				return AppConstants.SAME;
	
			loggedInUser.setPassword(passwordEncoder.encode(newPassword));
			userRepo.save(loggedInUser);
		}
		
		catch(Exception e)
		{
			System.out.println("Reset password exception " + e);
			return AppConstants.FAILURE;
		}
		
		return AppConstants.SUCCESS;
	}
	
	@Override
	public String resetForgotPassword(User user, ResetForgotPasswordFormBinding formData)
	{
		User save = null;
		try
		{
			user.setPassword(passwordEncoder.encode(formData.getPassword()));
			save = userRepo.save(user);
		}
		
		catch(Exception e)
		{
			System.out.println("Reset Forgot Password Exception " + e);
		}
			
		if(save != null)
			return AppConstants.SUCCESS;
		
		else
			return AppConstants.FAILURE;
	}

	@Override
	public Boolean saveImage(MultipartFile file, String fileName) 
	{
		String UPLOAD_DIRECTORY = System.getProperty(AppConstants.USER_DIR) + AppConstants.IMAGES_PATH;
		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
		try 
		{
			Files.write(fileNameAndPath, file.getBytes());
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public List<Contact> searchByName(String name, User user) 
	{
		return contactRepo.findByContactNameContainingAndUser(name, user);
	}

	@Override
	public Long getTotalUser() 
	{
		return userRepo.count()-1;
	}

	@Override
	public Long getVerifiedUsers() 
	{
		User userFilter = new User();
		userFilter.setAccountStatus(AppConstants.UNLOCKED);
		Example<User> example = Example.of(userFilter);
		return userRepo.count(example)-1;
	}

	@Override
	public Long getTotalContacts() 
	{
		return contactRepo.count();
	}

	@Override
	public Page<User> getAllUsers(Pageable pageable) 
	{
		User userFilter = new User();
		userFilter.setRole(AppConstants.ROLE_USER);
		Example<User> example = Example.of(userFilter);
		return userRepo.findAll(example, pageable);
	}

}
