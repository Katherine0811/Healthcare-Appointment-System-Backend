package com.tus.healthcare.service;

import com.tus.healthcare.model.Availability;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
	List<Availability> getAvailability(LocalDate start, LocalDate end, long providerId);
    Availability getAvailabilityByDate(LocalDate date, long providerId);
    Availability createDefaultAvailability(LocalDate date, long providerId);
    void updateAvailability(LocalDate date, long providerId, List<String> timeSlots);
}
