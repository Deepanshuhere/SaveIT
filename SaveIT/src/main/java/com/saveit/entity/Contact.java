package com.saveit.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = AppConstants.CONTACT_TABLE_NAME_DB)
public class Contact 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer id;
	
	private String contactName;
	
	private String work;
	
	private String email;
	
	private Long phone;
	
	private String contactImage;
	
	@Column(length = 200)
	private String description;	
	
	@CreationTimestamp
	@Column(name = AppConstants.CREATED_DATE_DB, updatable = false)
	private LocalDate createdDate;
	
	@UpdateTimestamp
	@Column(name = AppConstants.UPDATED_DATE_DB, insertable = false)
	private LocalDate updatedDate;
	
	@ManyToOne
	@JsonIgnore
	private User user;
}
