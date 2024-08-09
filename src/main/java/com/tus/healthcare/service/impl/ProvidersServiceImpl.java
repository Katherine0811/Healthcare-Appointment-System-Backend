package com.tus.healthcare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tus.healthcare.model.Providers;
import com.tus.healthcare.repository.ProvidersRepository;
import com.tus.healthcare.service.ProvidersService;

@Service
public class ProvidersServiceImpl implements ProvidersService{
	
    @Autowired
    private ProvidersRepository providersRepository;


	public List<String> findAllProviders() {
	    return providersRepository.findByUserRole("Provider").stream()
	                              .map(provider -> provider.getUser().getName())
	                              .collect(Collectors.toList());
	}

    public Providers findProviderByName(String name) {
        return providersRepository.findByUserName(name);
    }
}
