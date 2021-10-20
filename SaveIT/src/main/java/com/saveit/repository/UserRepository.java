package com.saveit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saveit.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	public User findByEmail(String email);
}
