package com.monitoring.documents.controllers;


import com.monitoring.documents.model.SubscriptionHelper;
import com.monitoring.documents.model.DocumentModel;
import com.monitoring.documents.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Document;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public List<DocumentModel> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @GetMapping(path = "{userId}")
    public List<DocumentModel> getAllSubscriptionsByUserId(@PathVariable("userId") Long id, @RequestParam(required = false) String description ) {
        if(description == null) {
            return subscriptionService.getAllSubscriptionsByUserId(id);
        } else {
            return subscriptionService.getSubscriptionByIdAndDescription(id, description);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @GetMapping(path = "id")
    public Optional<DocumentModel> getAllSubscriptionsById(@RequestParam("id") Long id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @GetMapping(path = "email")
    public List<DocumentModel> getAllSubscriptionsByEmail(@RequestParam("email") String email) {
        return subscriptionService.getAllByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @PostMapping
    public ResponseEntity<DocumentModel> save(@RequestBody DocumentModel subscription) {
        return ResponseEntity.ok(subscriptionService.save(subscription));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @PutMapping(path = "{id}")
    public void update(@PathVariable("id") Long id, @RequestBody DocumentModel subscription) {
        subscriptionService.update(id, subscription);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @PatchMapping(path = "{id}")
    public void personaliezdUpdate(@PathVariable("id") Long id, @RequestBody DocumentModel subscription) {
        subscriptionService.personalizedUpdate(id ,subscription);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @PatchMapping(path = "general/{id}")
    public void personaliezdUpdateGeneral(@PathVariable("id") Long id, @RequestBody DocumentModel subscription) {
        subscriptionService.personalizedUpdate(id ,subscription);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @GetMapping(path = "/expired")
    public List<DocumentModel> getAllExpiredSubscriptions() throws ParseException {
        return subscriptionService.findAllByExpireDate();
    }



    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @DeleteMapping(path = "{id}")
    public void remove(@PathVariable("id") Long id) {
        subscriptionService.remove(id);
    }

    @GetMapping(path = "/categories")
    public List<Integer> getAllCategories() {
        return this.subscriptionService.countByType();
    }

    @GetMapping(path = "/countAllMades")
    public List<SubscriptionHelper> countAllMades() {
        return subscriptionService.countAllMades();
    }
 
}
