package com.saveit.constants;

public class AppConstants 
{
	private AppConstants() 
	{}
	
	public static final String EMPTY_STRING = "";
	public static final String PATH = "/";
	public static final String HOME_PATH = "/home";
	public static final String LOGIN_PATH = "/login";
	public static final String SIGNUP_PATH = "/signup";
	public static final String REGISTER_PATH = "/do_register";
	public static final String FORGOT_PASSWORD_FORM_PATH = "/forgot-password-form";
	public static final String FORGOT_PASSWORD_EMAIL_PATH = "/forgot-password-email";
	public static final String NEW_PASSWORD_PATH = "/new-password/{email}";
	public static final String PROCESS_RESET_PASSWORD_PATH = "/process-reset-password";
	public static final String OTP_PATH = "/otp";
	public static final String SEND_OTP_PATH = "/sendOTP";
	public static final String RESEND_OTP_PATH = "/resendOTP";
	public static final String UNLOCK_ACCOUNT_PATH = "/unlock-account";
	public static final String USER_PATH = "/user";
	public static final String ADMIN_PATH = "/admin";
	public static final String LOCKED_PATH = "/locked";
	public static final String INDEX_PATH = "/index";
	public static final String ADD_CONTACT_PATH = "/add-contact";
	public static final String PROCESS_CONTACT_PATH = "/process-contact";
	public static final String VIEW_CONTACTS_PAGE_PATH = "/view-contacts/{page}";
	public static final String CONTACT_CONTACT_ID_PATH = "/contact/{contactId}";
	public static final String UPDATE_CONTACT_FORM_PATH = "/update-contact-form/{contactId}";
	public static final String DELETE_CONTACT_PATH = "/delete-contact/{contactId}";
	public static final String UPDATE_CONTACT_PATH = "/update-contact";
	public static final String USER_PROFILE_PATH = "/user-profile";
	public static final String UPDATE_PROFILE_FORM_PATH = "/update-profile-form";
	public static final String PROCESS_UPDATE_PROFILE_PATH = "/process-update-profile";
	public static final String SECURITY_PATH = "/security";
	public static final String UPDATE_PASSWORD_FORM_PATH = "/update-password-form";
	public static final String PROCESS_UPDATE_PASSWORD_PATH = "/process-update-password";
	public static final String SEARCH_PATH = "/search/{query}";
	public static final String VIEW_USERS_PATH = "/view-users/{page}";
	public static final String ADMIN_ALL_PATH = "/admin/**";
	public static final String USER_ALL_PATH = "/user/**";
	public static final String ALL_PATH = "/**";
	public static final String DO_LOGIN_PATH = "/dologin";
	public static final String TARGET_URL = "/login?error=true";
	public static final String ADMIN_INDEX_PATH = "/admin/index";
	public static final String USER_INDEX_PATH = "/user/index";
	
	public static final String LOCKED_VERIFY_ACCOUNT = "locked/verify_account";
	public static final String LOCKED_LOCKED_DASHBOARD = "locked/locked_dashboard";

	public static final String NORMAL_USER_DASHBOARD = "normal/user_dashboard";
	public static final String NORMAL_ADD_CONTACT = "normal/add_contact";
	public static final String NORMAL_VIEW_CONTACTS = "normal/view_contacts";
	public static final String NORMAL_VIEW_CONTACT = "normal/view_contact";
	public static final String NORMAL_UPDATE_CONTACT = "normal/update_contact";
	public static final String NORMAL_USER_PROFILE = "normal/user_profile";
	public static final String NORMAL_UPDATE_PROFILE = "normal/update_profile";
	public static final String NORMAL_SECURITY = "normal/security";
	public static final String NORMAL_UPDATE_PASSWORD = "normal/update_password";
	
	public static final String ADMIN_ADMIN_DASHBOARD = "admin/admin_dashboard";
	public static final String ADMIN_VIEW_USERS = "admin/view_users";

	public static final String TITLE = "title";
	public static final String OTP = "otp";
	public static final String HOME = "home";
	public static final String CUSTOMER_EMAIL = "customerEmail";
	public static final String MESSAGE = "message";
	public static final String LOGIN = "login";
	public static final String USER = "user";
	public static final String USER_CAPITAL = "USER";
	public static final String ADMIN_CAPITAL = "ADMIN";
	public static final String USERS = "users";
	public static final String SIGNUP = "signup";
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String FAILED = "FAILED";
	public static final String DUPLICATE = "DUPLICATE";
	public static final String ACCOUNT_REGISTERED = "Account Registered";
	public static final String EXCEPTION_MSG = "Something went wrong!!";
	public static final String ALERT_SUCCESS = "alert-success";
	public static final String ALERT_DANGER = "alert-danger";
	public static final String FORGOT = "forgot";
	public static final String FORGOT_PASSWORD = "forgot_password";
	public static final String EMAIL = "email";
	public static final String RESET_FORM = "resetForm";
	public static final String RESET_PASSWORD = "reset_password";
	public static final String INVALID_OTP = "INVALID-OTP";
	public static final String ACCOUNT_STATUS = "accountStatus";
	public static final String LOCKED = "LOCKED";
	public static final String UNLOCKED = "UNLOCKED";
	public static final String CONTACT = "contact";
	public static final String CONTACTS = "contacts";
	public static final String PROFILE_IMAGE = "profileImage";
	public static final String PAGE = "page";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String TOTAL_PAGES = "totalPages";
	public static final String CONTACT_ID = "contactId";
	public static final String PROFILE = "profile";
	public static final String RESET_PASSWORD_TEXT = "resetPassword";
	public static final String INCORRECT = "INCORRECT";
	public static final String SAME = "SAME";
	public static final String QUERY = "query";
	public static final String TOTAL_CONTACTS = "totalContacts";
	public static final String TOTAL_USERS = "totalUsers";
	public static final String VERIFIED_USERS = "verifiedUsers";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String RANDOM_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	public static final String USER_DIR = "user.dir";
	public static final String IMAGES_PATH = "/src/main/resources/static/img/contacts";

	public static final String HOME_TITLE = "SaveIT | Connect Smarter";
	public static final String LOGIN_TITLE = "SaveIT | Login";
	public static final String REGISTER_TITLE = "SaveIT | Register";
	public static final String ADD_CONTACT_TITLE = "SaveIT | Add Contact";
	public static final String CONTACT_PROFILE_TITLE = "SaveIT | Contact Profile";

	public static final String REDIRECT_FORGOT_PASSWORD_FORM_PATH = "redirect:/forgot-password-form";
	public static final String REDIRECT_USER_INDEX_PATH = "redirect:/user/index";
	public static final String REDIRECT_USER_LOCKED_PATH = "redirect:/user/locked";
	public static final String REDIRECT_USER_VIEW_CONTACTS_PATH = "redirect:/user/view-contacts/0";
	public static final String REDIRECT_USER_USER_PROFILE_PATH = "redirect:/user/user-profile";

	public static final String DUPLICATE_EMAIL_MSG = "Email already exist, Please enter unique email";
	public static final String UNREGISTERED_EMAIL_MSG = "Email id not registered!!";
	public static final String RESET_EMAIL_MSG = "Please check your email to Reset your Password";
	public static final String PASSWORD_UPDATED_MSG = "New password updated";
	public static final String OTP_RESEND_MSG = "OTP resent";
	public static final String ACCOUNT_VERIFIED_MSG = "Account Verified";
	public static final String ENTER_VALID_OTP_MSG = "Please enter valid OTP";
	public static final String CONTACT_SAVED_MSG = "Contact saved successfully";
	public static final String RECORD_UPDATED_MSG = "Record Updated!!";
	public static final String CONTACT_DELETED_MSG = "Contact deleted successfully";
	public static final String PROFILE_UPDATED_MSG = "Profile updated";
	public static final String ENTER_CORRECT_PASSWORD_MSG = "Please enter correct current password!!";
	public static final String SAME_OLD_PASSWORD_MSG = "New password cannot be same as old password!!";
	public static final String OTP_MAIL_SUBJECT_MSG = "Unlock Your Account ";
	public static final String RESET_PASSWORD_SUBJECT_MSG = "Reset your password ";
	public static final String USERNAME_NOT_FOUND_EXCEPTION_MSG = "Could not found user!!";
	
	public static final String UNLOCK_MAIL_BODY_FILE = "src/main/resources/email-body/UNLOCK-ACCOUNT-EMAIL-BODY-TEMPLATE.txt";
	public static final String FORGOT_PASSWORD_MAIL_BODY_FILE = "src/main/resources/email-body/FORGOT-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
	
	public static final String REPLACE_NAME = "{NAME}";
	public static final String REPLACE_OTP = "{OTP}";
	public static final String REPLACE_EMAIL = "{EMAIL}";
	
	public static final String CONTACT_TABLE_NAME_DB = "SAVEIT_CONTACT";
	public static final String USER_TABLE_NAME_DB = "SAVEIT_USER";
	
	public static final String CREATED_DATE_DB = "CREATED_DATE";
	public static final String UPDATED_DATE_DB = "UPDATED_DATE";
	
	public static final String ACCOUNT_STATUS_DB = "ACC_STATUS";
	
}
