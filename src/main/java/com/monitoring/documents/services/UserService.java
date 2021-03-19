package com.monitoring.documents.services;

import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if(!exists) {
            throw new IllegalStateException("Nu exista niciun cont asociat cu userid: " + userId);
        }

        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserEntity user) {

        UserEntity userFind = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                "N-a fost gasit niciun user cu id-ul " + userId
        ));

        verifyInputs(userFind, user.getEmail(), user.getProfile().getFirstName(), user.getProfile().getLastName(),
                user.getProfile().getPhoneNumber(), user.getProfile().getDateOfBirth(),
                user.getProfile().getAge(), user.getProfile().getPersonalIdentificationNumber(),
                user.getProfile().getAddress().getCounty(), user.getProfile().getAddress().getCity(),
                user.getProfile().getAddress().getTownShip(),user.getProfile().getAddress().getVillage(),
                user.getProfile().getAddress().getStreet(), user.getProfile().getAddress().getGateNumber());
    }

    public void verifyInputs(UserEntity userFind, String email, String firstName, String lastName,
                             String phoneNumber, Date dateOfBirth, Integer age, String personalIdentificationNumber,
                             String county, String city, String townShip, String village, String street, String gateNumber) {

        if(email != null && email.length() > 10 && !Objects.equals(userFind.getEmail(), email)) {

            Optional<UserEntity> userOptional = userRepository.findUserByEmail(email);

            if(userOptional.isPresent()) {
                throw new IllegalStateException("Email-ul este deja luat!");
            }

            userFind.setEmail(email);
        }

        if(firstName != null && firstName.length() > 4 && !Objects.equals(userFind.getProfile().getFirstName(), firstName)) {
            userFind.setEmail(firstName);
        }

        if(lastName != null && lastName.length() > 4 && !Objects.equals(userFind.getProfile().getLastName(), lastName)) {
            userFind.getProfile().setLastName(lastName);
        }

        if(phoneNumber != null && phoneNumber.length() > 4 && !Objects.equals(userFind.getProfile().getPhoneNumber(), phoneNumber)) {
            userFind.getProfile().setPhoneNumber(phoneNumber);
        }

        if(dateOfBirth != null && !Objects.equals(userFind.getProfile().getPhoneNumber(), dateOfBirth)) {
            userFind.getProfile().setDateOfBirth(dateOfBirth);
        }

        if(age != null && !Objects.equals(userFind.getProfile().getAge(), age)) {
            userFind.getProfile().setAge(age);
        }

        if(personalIdentificationNumber != null && !Objects.equals(userFind.getProfile().getPersonalIdentificationNumber(), personalIdentificationNumber)) {
            userFind.getProfile().setPersonalIdentificationNumber(personalIdentificationNumber);
        }

        if(county != null && !Objects.equals(userFind.getProfile().getAddress().getCounty(), county)) {
            userFind.getProfile().getAddress().setCounty(county);

        }

        if(city != null && !Objects.equals(userFind.getProfile().getAddress().getCity(), city)) {
            userFind.getProfile().getAddress().setCity(city);

        }

        if(townShip != null && !Objects.equals(userFind.getProfile().getAddress().getTownShip(), townShip)) {
            userFind.getProfile().getAddress().setTownShip(townShip);
        }

        if(village != null && !Objects.equals(userFind.getProfile().getAddress().getVillage(), village)) {
            userFind.getProfile().getAddress().setVillage(village);
        }

        if(street != null && !Objects.equals(userFind.getProfile().getAddress().getStreet(), street)) {
            userFind.getProfile().getAddress().setStreet(street);
        }

        if(gateNumber != null && !Objects.equals(userFind.getProfile().getAddress().getGateNumber(), gateNumber)) {
            userFind.getProfile().getAddress().setStreet(gateNumber);
        }

    }


}
