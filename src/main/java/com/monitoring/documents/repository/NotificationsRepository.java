package com.monitoring.documents.repository;

import com.monitoring.documents.model.NotificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationsEntity, Long> {
    Boolean existsByUserEmail(String email);
}
