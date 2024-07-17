package com.tus.healthcare.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tus.healthcare.dto.ErrorResponse;
import com.tus.healthcare.dto.UserDetailsDTO;
import com.tus.healthcare.exception.LoginException;
import com.tus.healthcare.exception.RegistrationException;
import com.tus.healthcare.model.Users;
import com.tus.healthcare.service.impl.UsersServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) {
        try {
            Users user = new Users();
            user.setName((String) payload.get("name"));
            user.setRole((String) payload.get("role"));
            user.setEmailAddress((String) payload.get("emailAddress"));
            user.setPassword((String) payload.get("password"));
            user.setPhoneNumber((String) payload.get("phoneNumber"));

            Users registeredUser = usersService.register(user, payload);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RegistrationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
        	UserDetailsDTO userDetails = usersService.login(loginRequest.getEmailAddress(), loginRequest.getPassword());
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

class LoginRequest {
    private String emailAddress;
    private String password;
    
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}