package com.tus.healthcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tus.healthcare.exception.AvailabilityException;
import com.tus.healthcare.model.Availability;
import com.tus.healthcare.model.Providers;
import com.tus.healthcare.repository.AvailabilityRepository;
import com.tus.healthcare.repository.ProvidersRepository;
import com.tus.healthcare.service.AvailabilityService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;
    
    @Autowired
    private ProvidersRepository providersRepository;

    public List<Availability> getAvailability(LocalDate startDate, LocalDate endDate, long providerId) {
        return availabilityRepository.findAllByProviderProviderIdAndAvailableDateBetween(providerId, startDate, endDate);
    }

    public Availability getAvailabilityByDate(LocalDate date, long providerId) {
        return availabilityRepository.findByProviderProviderIdAndAvailableDate(providerId, date)
            .orElseGet(() -> createDefaultAvailability(date, providerId));
    }

    public Availability createDefaultAvailability(LocalDate date, long providerId) {
        Providers provider = providersRepository.findById(providerId)
                .orElseThrow(() -> new AvailabilityException("Provider not found"));
        
        Availability availability = new Availability();
        availability.setProvider(provider);
        availability.setAvailableDate(date);
        availability.setAvailableTimes(List.of("10:00:00", "11:00:00", "14:00:00", "15:00:00"));
        availability.setCreatedAt(LocalDateTime.now());
        availability.setUpdatedAt(LocalDateTime.now());
        return availabilityRepository.save(availability);
    }

    public void updateAvailability(LocalDate date, long providerId, List<String> timeSlots) {
        Availability availability = getAvailabilityByDate(date, providerId);
        availability.setAvailableTimes(timeSlots);
        availability.setUpdatedAt(LocalDateTime.now());
        availabilityRepository.save(availability);
    }
}

