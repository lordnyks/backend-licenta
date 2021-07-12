package com.monitoring.documents.services;

import com.monitoring.documents.model.NotificationsEntity;
import com.monitoring.documents.model.DocumentModel;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.ParseException;
import java.util.*;

@Component
public class EmailScheduledService {
    private static final Logger log = LoggerFactory.getLogger(EmailScheduledService.class);


    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private EmailService emailService;



    private UserService userService;

    private SubscriptionService subscriptionService;

    private List<DocumentModel> documentModelWhichExpire;

    private NotificationsEntity notification;

    private Map<String, List<DocumentModel>> data;


    private HttpURLConnection httpConnection;


    public static final String ACCOUNT_SID = "ACf82ea83d54ab9384ece3cac71be5969a";
    public static final String AUTH_TOKEN = "32315f1fda13aa196467bab8766f13fc";


    @Autowired
    public EmailScheduledService(SubscriptionService service, UserService userservice) throws ParseException  {
        this.subscriptionService = service;
        this.userService = userservice;
    }

    @Scheduled(fixedRate = 10000)
    public void prepareEmails() throws ParseException, IOException {

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
        String message = "Salutare, în curând îți vor expira următoarele:\n";

            for(var z : i.getValue()) {
                    notification = new NotificationsEntity(i.getKey(), new Date(), true, z.getId());

                    if(z.getDescription().equals("rlb")) {
                        message += String.format("- Tipul: %s, Nume: %s, Prenume: %s, Banca: %s, Data plății: %s", interpretateDescription(z.getDescription()),
                                z.getLastName(), z.getFirstName(), z.getBanca(), z.getExpireDate());
                    } else if(z.getDescription().equals("impozit")) {
                        message += String.format("- Tipul: %s, Nume: %s, Prenume: %s, Mențiuni: %s, Data plății: %s, Adresa: %s", interpretateDescription(z.getDescription()),
                                z.getLastName(), z.getFirstName(), z.getMentions(), z.getExpireDate(), z.getFullAddress());
                    }
                    else if(z.getDescription().equals("ci")) {
                        message += String.format("- Tipul: %s, Nume: %s, Prenume: %s, Data expirării: %s.\n",
                                interpretateDescription(z.getDescription()), z.getFirstName(), z.getLastName(), z.getExpireDate());

                    } else {
                        message += String.format("- Tipul: %s, Număr mașină: %s, Marca: %s, Model: %s, Data expirării: %s.\n",
                                interpretateDescription(z.getDescription()), z.getPlateNumber(), z.getMade(), z.getModel(), z.getExpireDate());
                    }
                    notificationsService.save(notification);


            }
            String to = "+4" + userService.findPhoneNumber(i.getKey());
//            String sender = "4";
//            String body = message;
//
//            String charset = "UTF-8";
//            String query = String.format("to=%s&sender=%s&body=%s&apiKey=aTETOSm3ikDGm4wp4icWcSzKU6IbzxlBKsaWE6LC",
//                    URLEncoder.encode(to, charset),
//                    URLEncoder.encode(sender, charset),
//                    URLEncoder.encode(body, charset));
//
//            httpConnection = (HttpURLConnection) new URL(API_SMS + "?" + query).openConnection();
//            httpConnection.setRequestMethod("POST");
//            httpConnection.setRequestProperty("Content-Type", "application/json");

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message messageSMS = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber("+19527777479"),
                    message)
                    .create();
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
            case "rlb":
                output = "Rata la bancă";
                break;
            case "impozit":
                output = "Impozit";
                break;
        }

        return output;
    }



}
