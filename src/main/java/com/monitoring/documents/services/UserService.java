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
import java.util.Optional;

@Component
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<UserEntity> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
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


    public void updateUser(Long userId, UserEntity user) {

        UserEntity userFind = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException(
                "N-a fost gasit niciun user cu id-ul " + userId
        ));

        updateInput(userFind, user.getEmail(), user.getProfile().getFirstName(), user.getProfile().getLastName(),
                user.getProfile().getPhoneNumber(), user.getProfile().getDateOfBirth(),
                user.getProfile().getAge(), user.getProfile().getPersonalIdentificationNumber(),
                user.getProfile().getAddress().getCounty(), user.getProfile().getAddress().getCity(),
                user.getProfile().getAddress().getTownShip(),user.getProfile().getAddress().getVillage(),
                user.getProfile().getAddress().getStreet(), user.getProfile().getAddress().getGateNumber());
    }

    public void updateInput(UserEntity userFind, String email, String firstName, String lastName,
                            String phoneNumber, Date dateOfBirth, Integer age, String personalIdentificationNumber,
                            String county, String city, String townShip, String village, String street, String gateNumber) {

        userFind.setEmail(email);
        userFind.getProfile().setFirstName(firstName);
        userFind.getProfile().setLastName(lastName);
        userFind.getProfile().setPhoneNumber(phoneNumber);
        userFind.getProfile().setDateOfBirth(dateOfBirth);
        userFind.getProfile().setAge(age);
        userFind.getProfile().setPersonalIdentificationNumber(personalIdentificationNumber);
        userFind.getProfile().getAddress().setCounty(county);
        userFind.getProfile().getAddress().setCity(city);
        userFind.getProfile().getAddress().setTownShip(townShip);
        userFind.getProfile().getAddress().setVillage(village);
        userFind.getProfile().getAddress().setStreet(street);
        userFind.getProfile().getAddress().setGateNumber(gateNumber);
    }


}
