package com.monitoring.documents.services;

import com.monitoring.documents.model.NotificationsEntity;
import com.monitoring.documents.model.DocumentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component
public class EmailScheduledService {
    private static final Logger log = LoggerFactory.getLogger(EmailScheduledService.class);
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", new Date());
//    }

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private EmailService emailService;

    private SubscriptionService subscriptionService;

    private List<DocumentModel> documentModelWhichExpire;

    private NotificationsEntity notification;

    private Map<String, List<DocumentModel>> data;



    @Autowired
    public EmailScheduledService(SubscriptionService service) throws ParseException  {
        this.subscriptionService = service;
    }

    @Scheduled(fixedRate = 10000)
    public void prepareEmails() throws ParseException {

        this.documentModelWhichExpire = subscriptionService.findAllByExpireDate();


        data = new HashMap<>();
        for(var s : documentModelWhichExpire) {
            List<DocumentModel> temp;
            if(!data.containsKey(s.getEmail())) {
                temp = new ArrayList<>();
            } else {
                temp = data.get(s.getEmail());
            }

            temp.add(s);

            data.put(s.getEmail(), temp);
        }



        for(Map.Entry<String, List<DocumentModel>> i : data.entrySet()) {
        String message = "Salutare, în curând îți vor expira următoarele documente:\n";

            for(var z : i.getValue()) {
                    notification = new NotificationsEntity(i.getKey(), new Date(), true, z.getId());

                    if(!z.getDescription().equals("ci")) {
                        message += String.format("Tipul documentului: %s, Număr mașină: %s, Marca: %s, Model: %s, Data expirării: %s.\n",
                                interpretateDescription(z.getDescription()), z.getPlateNumber(), z.getMade(), z.getModel(), z.getExpireDate());
                    } else {
                        message += String.format("Tipul documentului: %s, Nume: %s, Prenume: %s Data expirării: %s.\n",
                                interpretateDescription(z.getDescription()), z.getFirstName(), z.getLastName(), z.getExpireDate());
                    }
                    notificationsService.save(notification);
            }

            emailService.sendMessage(i.getKey(), "Documentele tale expiră", message);
        }



    }

    private String interpretateDescription(String description) {
        String output = "error";

        switch (description) {
            case "itp":
                output = "Inspecție tehnică periodică";
                break;
            case "rca":
                output = "Asigurare RCA";
                break;
            case "ci":
                output = "Carte de identitate";
                break;
            case "rov":
                output = "Rovinietă";
                break;
        }

        return output;
    }



}
