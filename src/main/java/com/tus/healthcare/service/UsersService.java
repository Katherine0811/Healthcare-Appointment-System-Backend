package com.tus.healthcare.service;

import java.util.Map;

import com.tus.healthcare.dto.UserDetailsDTO;
import com.tus.healthcare.exception.LoginException;
import com.tus.healthcare.exception.RegistrationException;
import com.tus.healthcare.model.Users;

public interface UsersService {
    Users register(Users user, Map<String, Object> payload) throws RegistrationException;
    UserDetailsDTO login(String emailAddress, String password) throws LoginException;
	UserDetailsDTO updateUser(Long userId, UserDetailsDTO userDetailsDTO);
	void deleteUser(Long userId);
}