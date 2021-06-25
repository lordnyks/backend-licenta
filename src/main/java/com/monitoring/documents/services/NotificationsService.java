package com.monitoring.documents.services;

import com.monitoring.documents.model.NotificationsEntity;
import com.monitoring.documents.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;


    @Autowired
    private EmailService emailService;

    List<NotificationsEntity> notifications;


    public void save(NotificationsEntity notificationsEntity) {
        notificationsRepository.save(notificationsEntity);
    }

    public Boolean existsEmail(String email) {
        return notificationsRepository.existsByUserEmail(email);
    }



    private void marker(NotificationsEntity notification) {
        NotificationsEntity notificationTemp = notificationsRepository.findById(notification.getId()).orElseThrow(() -> new IllegalStateException("Nu exista o notificare cu acest id"));

        notificationTemp.setSent(true);
        notificationsRepository.save(notificationTemp);
    }

}
