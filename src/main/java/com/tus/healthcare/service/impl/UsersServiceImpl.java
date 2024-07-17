package com.tus.healthcare.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tus.healthcare.dto.UserDetailsDTO;
import com.tus.healthcare.exception.LoginException;
import com.tus.healthcare.exception.RegistrationException;
import com.tus.healthcare.model.Patients;
import com.tus.healthcare.model.Providers;
import com.tus.healthcare.model.Users;
import com.tus.healthcare.repository.PatientsRepository;
import com.tus.healthcare.repository.ProvidersRepository;
import com.tus.healthcare.repository.UsersRepository;
import com.tus.healthcare.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private ProvidersRepository providersRepository;
    
    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
    
    @Transactional
    public Users register(Users user, Map<String, Object> payload) throws RegistrationException {
        if (usersRepository.existsByEmailAddress(user.getEmailAddress())) {
            throw new RegistrationException("Email address is already in use.");
        }

        Users savedUser = usersRepository.save(user);

        if ("Patient".equals(user.getRole())) {
            Patients patient = new Patients();
            patient.setUser(savedUser);
            patient.setDateOfBirth(LocalDate.parse((String) payload.get("dateOfBirth")));
            patient.setGender((String) payload.get("gender"));
            patient.setHomeAddress((String) payload.get("homeAddress"));
            patient.setInsuranceNumber((String) payload.get("insuranceNumber"));
            patientsRepository.save(patient);
        } else if ("Provider".equals(user.getRole())) {
            Providers provider = new Providers();
            provider.setUser(savedUser);
            provider.setSpecialization((String) payload.get("specialization"));
            provider.setLicenseNumber((String) payload.get("licenseNumber"));
            providersRepository.save(provider);
        }

        return savedUser;
    }

    public UserDetailsDTO login(String emailAddress, String password) throws LoginException {
        Users user = usersRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new LoginException("User not found");
        }
//        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
        if (!password.equals(user.getPassword())) {
        	throw new LoginException("Invalid password");
        }

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserId(user.getUserId());
        userDetailsDTO.setName(user.getName());
        userDetailsDTO.setRole(user.getRole());
        userDetailsDTO.setEmailAddress(user.getEmailAddress());
        userDetailsDTO.setPhoneNumber(user.getPhoneNumber());

        if ("Patient".equals(user.getRole())) {
            Patients patient = patientsRepository.findByUser(user);
            if (patient != null) {
                userDetailsDTO.setDateOfBirth(patient.getDateOfBirth());
                userDetailsDTO.setGender(patient.getGender());
                userDetailsDTO.setHomeAddress(patient.getHomeAddress());
                userDetailsDTO.setInsuranceNumber(patient.getInsuranceNumber());
            }
        } else if ("Provider".equals(user.getRole())) {
            Providers provider = providersRepository.findByUser(user);
            if (provider != null) {
                userDetailsDTO.setSpecialization(provider.getSpecialization());
                userDetailsDTO.setLicenseNumber(provider.getLicenseNumber());
            }
        }

        return userDetailsDTO;
    }
}
