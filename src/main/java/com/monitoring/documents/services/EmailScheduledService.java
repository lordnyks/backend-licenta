package com.monitoring.documents.services;

import com.monitoring.documents.model.Subscriptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class EmailScheduledService {
    private static final Logger log = LoggerFactory.getLogger(EmailScheduledService.class);
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", new Date());
//    }

    private SubscriptionService subscriptionService;

    private List<Subscriptions> subscriptionsWhichExpire;

    @Autowired
    public EmailScheduledService(SubscriptionService service) throws ParseException  {
        this.subscriptionService = service;
    }

    @Scheduled(fixedRate = 5000)
    public void sendEmails() throws ParseException {

        this.subscriptionsWhichExpire = subscriptionService.findAllByExpireDate();

        for(var s : subscriptionsWhichExpire) {
            log.info("Email: {}", s.getEmail());
        }
    }
}
