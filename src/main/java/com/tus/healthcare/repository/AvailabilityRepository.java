package com.tus.healthcare.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
	Optional<Availability> findByProviderProviderIdAndAvailableDate(long providerId, LocalDate availableDate);
	List<Availability> findAllByProviderProviderIdAndAvailableDateBetween(long providerId, LocalDate startDate,
			LocalDate endDate);
}