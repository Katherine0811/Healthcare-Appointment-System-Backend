package com.tus.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tus.healthcare.model.Providers;
import com.tus.healthcare.service.ProvidersService;

@RestController
@RequestMapping("/providers")
public class ProvidersController {

    @Autowired
    private ProvidersService providersService;

    @GetMapping
    public List<String> getProviders() {
        return providersService.findAllProviders();
    }

    @GetMapping("/name")
    public Providers getProviderByName(@RequestParam String name) {
        return providersService.findProviderByName(name);
    }
}