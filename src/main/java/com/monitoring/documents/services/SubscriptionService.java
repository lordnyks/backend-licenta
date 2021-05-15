package com.monitoring.documents.services;

import com.monitoring.documents.model.Subscriptions;
import com.monitoring.documents.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Component
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscriptions> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }


    public Optional<Subscriptions> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public List<Subscriptions> getSubscriptionByIdAndDescription(Long id, String description) {
        return subscriptionRepository.findAllByUserIdAndDescription(id, description);
    }

    public List<Subscriptions> getAllSubscriptionsByUserId(Long id) {
        return subscriptionRepository.findAllByUserId(id);
    }

    public Subscriptions save(@RequestBody Subscriptions subscription) {
       return subscriptionRepository.save(subscription);
    }

    public void remove(Long id) {
        boolean exists = subscriptionRepository.existsById(id);

        if(!exists) {
            throw new IllegalStateException("Nu exista niciun abonament cu id-ul: " + id);
        }

        subscriptionRepository.deleteById(id);

    }

}
