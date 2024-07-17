package com.tus.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Availability;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}