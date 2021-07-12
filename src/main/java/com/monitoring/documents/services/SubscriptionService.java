package com.monitoring.documents.services;

import com.monitoring.documents.model.SubscriptionHelper;
import com.monitoring.documents.model.DocumentModel;
import com.monitoring.documents.model.SubscriptionSaverHelper;
import com.monitoring.documents.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


@Component
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<DocumentModel> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }


    public Optional<DocumentModel> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public List<DocumentModel> getSubscriptionByIdAndDescription(Long id, String description) {
        return subscriptionRepository.findAllByUserIdAndDescription(id, description);
    }

    public List<DocumentModel> getAllSubscriptionsByUserId(Long id) {
        return subscriptionRepository.findAllByUserId(id);
    }

    public List<DocumentModel> getAllByEmail(String email) {
        return subscriptionRepository.findAllByEmail(email);
    }

    public DocumentModel save(@RequestBody DocumentModel subscription) {


        if(subscription.getDescription().equals("itp") || subscription.getDescription().equals("rov") || subscription.getDescription().equals("rca")) {

            if(subscriptionRepository.existsEmailByPlateNumber(subscription.getPlateNumber())) {
                List<DocumentModel> tempDocument = subscriptionRepository.findByPlateNumber(subscription.getPlateNumber());

                if(!subscription.getEmail().equals(tempDocument.get(0).getEmail()))
                    throw new IllegalStateException("Acest număr de mașină este deja luat!");
            }
        }
        if(subscription.getExpireDate().compareTo(LocalDate.now()) == 0)
            throw new IllegalStateException("Ați setat data de astăzi!");

        if(subscription.getExpireDate().compareTo(LocalDate.now()) < 0)
            throw new IllegalStateException("Documentul poate fi deja expirat!");


        return subscriptionRepository.save(subscription);

    }

    public void update(Long id, DocumentModel documentModel) {
        DocumentModel subscriptionTemp = subscriptionRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Id-ul nu exista: " + id
        ));
        List<DocumentModel> tempDocument = subscriptionRepository.findByPlateNumber(documentModel.getPlateNumber());



            if(subscriptionTemp.getDescription().equals("itp") || subscriptionTemp.getDescription().equals("rov") || subscriptionTemp.getDescription().equals("rca")) {

                if(subscriptionRepository.existsEmailByPlateNumber(documentModel.getPlateNumber())) {

                    if(!documentModel.getEmail().equals(tempDocument.get(0).getEmail()))
                        throw new IllegalStateException("Acest număr de mașină este deja luat!");
                }
            }


        if(documentModel.getExpireDate().compareTo(LocalDate.now()) == 0)
            throw new IllegalStateException("Ați setat data de astăzi!");
        if(documentModel.getExpireDate().compareTo(LocalDate.now()) <= 0)
            throw new IllegalStateException("Documentul poate fi deja expirat!");
        
        subscriptionTemp.setExpireDate(documentModel.getExpireDate());
        subscriptionTemp.setPlateNumber(documentModel.getPlateNumber());
        subscriptionTemp.setMade(documentModel.getMade());
        subscriptionTemp.setModel(documentModel.getModel());
        subscriptionTemp.setFirstName(documentModel.getFirstName());
        subscriptionTemp.setLastName(documentModel.getLastName());
        subscriptionTemp.setPersonalIdentificationNumber(documentModel.getPersonalIdentificationNumber());
        subscriptionTemp.setBanca(documentModel.getBanca());
        subscriptionTemp.setMentions(documentModel.getMentions());
        subscriptionTemp.setFullAddress(documentModel.getFullAddress());


        subscriptionRepository.save(subscriptionTemp);

    }

    public List<DocumentModel> findAllByExpireDate() throws ParseException {
        LocalDate date = LocalDate.now();

        LocalDate myTempDate = date.plusDays(5);


        return subscriptionRepository.findAllByExpireDate(myTempDate);
    }



    public void personalizedUpdate(Long id, DocumentModel documentModel/*String firstName, String lastName, Date expireDate, String plateNumber, String made, String model, String description*/) {

        DocumentModel documentModelTemp = subscriptionRepository.findById(id).orElseThrow(() -> new IllegalStateException("Nu gasesc un user asociat cu acest id: " + id));

        if(subscriptionRepository.existsEmailByPlateNumber(documentModel.getPlateNumber())) {
            if(!documentModel.getEmail().equals(documentModelTemp.getEmail()))
                throw new IllegalStateException("Acest număr de mașină este deja luat!");
        }

        documentModelTemp.setFirstName(documentModel.getFirstName());
        documentModelTemp.setLastName(documentModel.getLastName());
        documentModelTemp.setExpireDate(documentModel.getExpireDate());
        documentModelTemp.setPlateNumber(documentModel.getPlateNumber());
        documentModelTemp.setMade(documentModel.getMade());
        documentModelTemp.setModel(documentModel.getModel());
        documentModelTemp.setDescription(documentModel.getDescription());




//        subscriptionsTemp.setFirstName(firstName);
//        subscriptionsTemp.setLastName(lastName);
//        subscriptionsTemp.setExpireDate(expireDate);
//        subscriptionsTemp.setPlateNumber(plateNumber);
//        subscriptionsTemp.setMade(made);
//        subscriptionsTemp.setModel(model);
//        subscriptionsTemp.setDescription(description);

    }

    public void personalizedUpdateGeneral(Long id, DocumentModel documentModel/*String firstName, String lastName, Date expireDate, String plateNumber, String made, String model, String description*/) {

        DocumentModel documentModelTemp = subscriptionRepository.findById(id).orElseThrow(() -> new IllegalStateException("Nu gasesc un user asociat cu acest id: " + id));

        if(subscriptionRepository.existsEmailByPlateNumber(documentModel.getPlateNumber())) {
            if(!documentModel.getEmail().equals(documentModelTemp.getEmail()))
                throw new IllegalStateException("Acest număr de mașină este deja luat!");
        }
        documentModelTemp.setExpireDate(documentModel.getExpireDate());
        documentModelTemp.setPlateNumber(documentModel.getPlateNumber());
        documentModelTemp.setMade(documentModel.getMade());
        documentModelTemp.setModel(documentModel.getModel());
        documentModelTemp.setDescription(documentModel.getDescription());




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

    public List<Integer> countByType() {
        Integer itp = subscriptionRepository.countByType("itp") != null ? subscriptionRepository.countByType("itp") : 0;
        Integer rca = subscriptionRepository.countByType("rca")!= null  ? subscriptionRepository.countByType("rca"): 0;
        Integer ci = subscriptionRepository.countByType("ci") != null ? subscriptionRepository.countByType("ci"): 0;
        Integer rov = subscriptionRepository.countByType("rov") != null ? subscriptionRepository.countByType("rov"): 0;
        Integer rlb = subscriptionRepository.countByType("rlb") != null ? subscriptionRepository.countByType("rlb"): 0;
        Integer impozit = subscriptionRepository.countByType("impozit") != null ? subscriptionRepository.countByType("impozit"): 0;
//        Integer itp = subscriptionRepository.countByType("itp").orElseThrow(() -> new IllegalStateException("A apărut o eroarea la preluarea tipului de document: itp."));

        return Arrays.asList(new Integer[] {itp,rca,ci,rov,rlb,impozit});
    }


    public List<SubscriptionHelper> countAllMades() {
        return subscriptionRepository.countAllMades();
    }
}
