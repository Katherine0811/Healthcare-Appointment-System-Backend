package com.tus.healthcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tus.healthcare.model.Availability;
import com.tus.healthcare.service.AvailabilityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public List<Availability> getAvailability(
        @RequestParam LocalDate start,
        @RequestParam LocalDate end,
        @RequestParam long providerId
    ) {
        return availabilityService.getAvailability(start, end, providerId);
    }

    @GetMapping("/date")
    public List<Availability> getAvailabilityByDate(
        @RequestParam LocalDate date,
        @RequestParam long providerId
    ) {
        return List.of(availabilityService.getAvailabilityByDate(date, providerId));
    }

    @PostMapping
    public List<Availability> createDefaultAvailability(
        @RequestParam LocalDate date,
        @RequestParam long providerId
    ) {
        return List.of(availabilityService.createDefaultAvailability(date, providerId));
    }

    @PutMapping("/update")
    public void updateAvailability(
        @RequestParam LocalDate date,
        @RequestParam long providerId,
        @RequestBody List<String> timeSlots
    ) {
        availabilityService.updateAvailability(date, providerId, timeSlots);
    }
}

