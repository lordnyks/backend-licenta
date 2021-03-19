package com.monitoring.documents.services;

import com.monitoring.documents.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    

}
