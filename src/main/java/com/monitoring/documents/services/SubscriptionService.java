package com.monitoring.documents.services;

import com.monitoring.documents.model.Subscriptions;
import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public List<Subscriptions> getAllByEmail(String email) {
        return subscriptionRepository.findAllByEmail(email);
    }

    public Subscriptions save(@RequestBody Subscriptions subscription) {

       if(subscription.getExpireDate().compareTo(new Date()) < 0)
           throw new IllegalStateException("Data expirării este mai mică decât data de astăzi.");

       return subscriptionRepository.save(subscription);
    }

    public void update(Long id, Subscriptions subscriptions) {
        Subscriptions subscriptionTemp = subscriptionRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Id-ul nu exista: " + id
        ));

        subscriptionTemp.setEmail(subscriptions.getEmail());
        subscriptionTemp.setFirstName(subscriptions.getFirstName());
        subscriptionTemp.setLastName(subscriptions.getLastName());
        subscriptionTemp.setExpireDate(subscriptions.getExpireDate());
        subscriptionTemp.setPlateNumber(subscriptions.getPlateNumber());
        subscriptionTemp.setMade(subscriptions.getMade());
        subscriptionTemp.setModel(subscriptions.getModel());
        subscriptionTemp.setDescription(subscriptions.getDescription());

        subscriptionRepository.save(subscriptionTemp);

    }

    public List<Subscriptions> findAllByExpireDate() throws ParseException {
        SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 5);
        String output = myDate.format(calendar.getTime());

        Date outputer = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(output);

        return subscriptionRepository.findAllByExpireDate(outputer);
    }



    public void personalizedUpdate(Long id, Subscriptions subscriptions/*String firstName, String lastName, Date expireDate, String plateNumber, String made, String model, String description*/) {

        Subscriptions subscriptionsTemp = subscriptionRepository.findById(id).orElseThrow(() -> new IllegalStateException("Nu gasesc un user asociat cu acest id: " + id));

        subscriptionsTemp.setFirstName(subscriptions.getFirstName());
        subscriptionsTemp.setLastName(subscriptions.getLastName());
        subscriptionsTemp.setExpireDate(subscriptions.getExpireDate());
        subscriptionsTemp.setPlateNumber(subscriptions.getPlateNumber());
        subscriptionsTemp.setMade(subscriptions.getMade());
        subscriptionsTemp.setModel(subscriptions.getModel());
        subscriptionsTemp.setDescription(subscriptions.getDescription());


//        subscriptionsTemp.setFirstName(firstName);
//        subscriptionsTemp.setLastName(lastName);
//        subscriptionsTemp.setExpireDate(expireDate);
//        subscriptionsTemp.setPlateNumber(plateNumber);
//        subscriptionsTemp.setMade(made);
//        subscriptionsTemp.setModel(model);
//        subscriptionsTemp.setDescription(description);

    }



    public void remove(Long id) {
        boolean exists = subscriptionRepository.existsById(id);

        if(!exists) {
            throw new IllegalStateException("Nu exista niciun abonament cu id-ul: " + id);
        }

        subscriptionRepository.deleteById(id);

    }

}
