package com.tus.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Providers;
import com.tus.healthcare.model.Users;

@Repository
public interface ProvidersRepository extends JpaRepository<Providers, Long> {
	Providers findByUser(Users user);
}
