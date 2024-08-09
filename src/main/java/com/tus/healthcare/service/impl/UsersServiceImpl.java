package com.tus.healthcare.service.impl;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tus.healthcare.dto.UserDetailsDTO;
import com.tus.healthcare.exception.LoginException;
import com.tus.healthcare.exception.RegistrationException;
import com.tus.healthcare.exception.UserNotFoundException;
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
            	userDetailsDTO.setPatientId(patient.getPatientId());
                userDetailsDTO.setDateOfBirth(patient.getDateOfBirth());
                userDetailsDTO.setGender(patient.getGender());
                userDetailsDTO.setHomeAddress(patient.getHomeAddress());
                userDetailsDTO.setInsuranceNumber(patient.getInsuranceNumber());
            }
        } else if ("Provider".equals(user.getRole())) {
            Providers provider = providersRepository.findByUser(user);
            if (provider != null) {
            	userDetailsDTO.setProviderId(provider.getProviderId());
                userDetailsDTO.setSpecialization(provider.getSpecialization());
                userDetailsDTO.setLicenseNumber(provider.getLicenseNumber());
            }
        }

        return userDetailsDTO;
    }
    
    public UserDetailsDTO updateUser(Long userId, UserDetailsDTO userDetailsDTO) {
        Optional<Users> optionalUser = usersRepository.findById(userId);
        Users user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userDetailsDTO.setUserId(user.getUserId());
        user.setName(userDetailsDTO.getName());
        user.setEmailAddress(userDetailsDTO.getEmailAddress());
        user.setPhoneNumber(userDetailsDTO.getPhoneNumber());
        usersRepository.save(user);

        if ("Patient".equals(userDetailsDTO.getRole())) {
            Patients patient = patientsRepository.findByUser(user);
            if (patient != null) {
            	userDetailsDTO.setPatientId(patient.getPatientId());
                patient.setDateOfBirth(userDetailsDTO.getDateOfBirth());
                patient.setGender(userDetailsDTO.getGender());
                patient.setHomeAddress(userDetailsDTO.getHomeAddress());
                patient.setInsuranceNumber(userDetailsDTO.getInsuranceNumber());
                patientsRepository.save(patient);
            }
        } else if ("Provider".equals(userDetailsDTO.getRole())) {
            Providers provider = providersRepository.findByUser(user);
            if (provider != null) {
            	userDetailsDTO.setProviderId(provider.getProviderId());
                provider.setSpecialization(userDetailsDTO.getSpecialization());
                provider.setLicenseNumber(userDetailsDTO.getLicenseNumber());
                providersRepository.save(provider);
            }
        }
        
        return userDetailsDTO;
    }

    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }
}
