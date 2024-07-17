package com.tus.healthcare.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "providerId")
    private Providers provider;

    private LocalDate availableDate;
    private LocalTime availableTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
	public Long getAvailabilityId() {
		return availabilityId;
	}
	public void setAvailabilityId(Long availabilityId) {
		this.availabilityId = availabilityId;
	}
	
	public Providers getProvider() {
		return provider;
	}
	public void setProvider(Providers provider) {
		this.provider = provider;
	}
	
	public LocalDate getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}
	
	public LocalTime getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(LocalTime availableTime) {
		this.availableTime = availableTime;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}