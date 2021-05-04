package com.monitoring.documents.repository;

import com.monitoring.documents.model.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    Optional<Subscriptions> findById(Long userId);
    List<Subscriptions> findAllByUserId(Long userid);

}
