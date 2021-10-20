package com.saveit.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.saveit.constants.AppConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppConstants.USER_TABLE_NAME_DB)
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String name;

	@Column(unique = true)
	private String email;

	private String password;
	
	private Long phone;
	
	@Column(length = 200)
	private String about;
 	
	private String contactImage;
	
	private String role;
	
	private String OTP;
	
	@Column(name = AppConstants.ACCOUNT_STATUS_DB)
	private String accountStatus;

	@CreationTimestamp
	@Column(name = AppConstants.CREATED_DATE_DB, updatable = false)
	private LocalDate createdDate;
	
	@UpdateTimestamp
	@Column(name = AppConstants.UPDATED_DATE_DB, insertable = false)
	private LocalDate updatedDate;

	/*
	 * CASCADE 
	 * =======
	 * -> When you save user than it's related contacts will also be saved
	 * -> WHen you delete user than it's related contacts will also be deleted
	 *
	 *
	 * FETCH
	 * =====
	 * EAGER -> Fetch all contacts when you are fetching users
	 * LAZY  -> Fetch all contacts when you call this method 
	 * 
	 * mappedBy
	 * ========
	 * -> New table will not be created which includes USER ID and it's corresponding CONTACT ID
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = AppConstants.USER)
	private List<Contact> contacts = new ArrayList<>();
}
