package com.tus.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByEmailAddress(String emailAddress);
	boolean existsByEmailAddress(String emailAddress);
}