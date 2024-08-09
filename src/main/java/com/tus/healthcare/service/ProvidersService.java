package com.tus.healthcare.service;

import java.util.List;

import com.tus.healthcare.model.Providers;

public interface ProvidersService {
	List<String> findAllProviders();
	Providers findProviderByName(String name);

}
