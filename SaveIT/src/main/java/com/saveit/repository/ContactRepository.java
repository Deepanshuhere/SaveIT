package com.saveit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saveit.entity.Contact;
import com.saveit.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>
{
	public Page<Contact> findByUser(User user, Pageable pageable);
	public List<Contact> findByContactNameContainingAndUser(String name, User user);
}
