package com.monitoring.documents.controllers;


import com.monitoring.documents.model.Subscriptions;
import com.monitoring.documents.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    @GetMapping
    public List<Subscriptions> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @GetMapping(path = "{userId}")
    public List<Subscriptions> getAllSubscriptionsByUserId(@PathVariable("userId") Long id, @RequestParam(required = false) String description ) {
        if(description == null) {
            return subscriptionService.getAllSubscriptionsByUserId(id);
        } else {
            return subscriptionService.getSubscriptionByIdAndDescription(id, description);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @PostMapping
    public ResponseEntity<Subscriptions> save(@RequestBody Subscriptions subscription) {
        return ResponseEntity.ok(subscriptionService.save(subscription));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @DeleteMapping(path = "{id}")
    public void remove(@PathVariable("id") Long id) {
        subscriptionService.remove(id);
    }
 
}
