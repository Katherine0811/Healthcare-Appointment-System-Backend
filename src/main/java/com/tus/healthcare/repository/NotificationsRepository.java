package com.tus.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.healthcare.model.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
}