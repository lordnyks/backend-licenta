package com.monitoring.documents.controllers;


import com.monitoring.documents.model.Subscriptions;
import com.monitoring.documents.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(path = "{userId}")
    public List<Subscriptions> getAllSubscriptionsByUserId(@PathVariable("userId") Long id) {
        return subscriptionService.getAllSubscriptionsByUserId(id);
    }

}
