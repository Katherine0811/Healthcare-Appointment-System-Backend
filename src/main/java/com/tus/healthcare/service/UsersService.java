package com.tus.healthcare.service;

import java.util.List;
import com.tus.healthcare.model.Users;

public interface UsersService {
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users saveUser(Users user);
    void deleteUser(Long id);
}