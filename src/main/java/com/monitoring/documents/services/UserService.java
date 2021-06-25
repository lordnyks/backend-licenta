package com.monitoring.documents.services;

import com.monitoring.documents.model.ERole;
import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Component
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Value("${admin.account}")
    private String adminAccount;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


    public List<UserEntity> getUserByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }

    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public List<String> getAllUsersByEmail() {
        return userRepository.getAllEmails();
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
                            String phoneNumber, LocalDate dateOfBirth, Integer age, String personalIdentificationNumber,
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

    public ERole getRole(String email) {
        UserEntity userTemp = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalStateException("Utilizatorul " + email + " nu există în baza de date!"));

        return userTemp.getRole();
    }

    public List<Integer> countGenders() {
        Integer male = userRepository.countGenders("masculin").orElseThrow(() -> new IllegalStateException("Eroare la preluarea numarului de persoane cu genul masculin."));
        Integer female = userRepository.countGenders("feminin").orElseThrow(() -> new IllegalStateException("Eroare la preluarea numarului de persoane cu genul feminin."));


        return Arrays.asList(new Integer[]{male, female});
    }

    public Optional<Integer> countAllUsers() {
        return userRepository.countById();
    }

    public void updateRole(String email, String role, String asker) {
        UserEntity userTemp = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalStateException("Utilizatorul " + email + " nu există în baza de date!"));

        UserEntity authorityAsker = userRepository.findUserByEmail(asker).orElseThrow(() -> new IllegalStateException("Eroare! Contacteaza un administrator! Email: " + adminAccount));

        if(adminAccount.equals(userTemp.getUsername()))
            throw new IllegalStateException("Nu poti schimba rolul acestui utilizator!");

        if(userTemp.getUsername().equals(authorityAsker.getUsername())) {
            throw new IllegalStateException("Nu poti sa-ti schimbi propriul tau rol!");
        }



        if(authorityAsker.getRole().toString() == "ROLE_SUPERVISOR" || authorityAsker.getRole().toString() == "ROLE_ADMIN") {

            switch (role) {
                case "ROLE_MEMBER":
                    if((userTemp.getRole().toString().equals("ROLE_ADMIN") || userTemp.getRole().toString().equals("ROLE_SUPERVISOR")) && !(adminAccount.equals(authorityAsker.getUsername())))
                        throw new IllegalStateException("Doar administratorul poate modifica intr-un rol mai mic.");
                    userTemp.setRole(ERole.ROLE_MEMBER);

                    break;
                case "ROLE_HELPER":
                    if((userTemp.getRole().toString().equals("ROLE_ADMIN") || userTemp.getRole().toString().equals("ROLE_SUPERVISOR")) && !adminAccount.equals(authorityAsker.getUsername()) )
                        throw new IllegalStateException("Doar administratorul poate modifica intr-un rol mai mic.");

                    userTemp.setRole(ERole.ROLE_HELPER);
                    break;
                case "ROLE_MODERATOR":
                    if((userTemp.getRole().toString().equals("ROLE_ADMIN") || userTemp.getRole().toString().equals("ROLE_SUPERVISOR")) && !adminAccount.equals(authorityAsker.getUsername()) )
                        throw new IllegalStateException("Doar administratorul poate modifica intr-un rol mai mic.");

                    userTemp.setRole(ERole.ROLE_MODERATOR);
                    break;
                case "ROLE_SUPERVISOR":
                    if(!(/*authorityAsker.getRole().toString() == "ROLE_ADMIN" && */adminAccount.equals(authorityAsker.getUsername())))
                        throw new IllegalStateException("Numai un administrator poate efectua această acțiune!");
                    userTemp.setRole(ERole.ROLE_SUPERVISOR);
                    break;
                case "ROLE_ADMINISTRATOR":
                    if(!(/*authorityAsker.getRole().toString() == "ROLE_ADMIN"&&*/ adminAccount.equals(authorityAsker.getUsername())))
                        throw new IllegalStateException("Numai un administrator poate efectua această acțiune!");
                    userTemp.setRole(ERole.ROLE_ADMIN);
                    break;
                default:
                    throw new IllegalStateException("Nu există rolul " + role + ".");

            }
        } else {
            throw new IllegalStateException("Nu ai acces pentru a modifica rolurile!");
        }


    }

    public List<Integer> getCountRoles() {
        Integer admins = userRepository.countRoles(ERole.ROLE_ADMIN) != null ? userRepository.countRoles(ERole.ROLE_ADMIN) : 0;
        Integer supervisors = userRepository.countRoles(ERole.ROLE_SUPERVISOR) != null ? userRepository.countRoles(ERole.ROLE_SUPERVISOR) : 0;
        Integer moderators = userRepository.countRoles(ERole.ROLE_MODERATOR) != null ? userRepository.countRoles(ERole.ROLE_MODERATOR) : 0;
        Integer helpers = userRepository.countRoles(ERole.ROLE_HELPER) != null ? userRepository.countRoles(ERole.ROLE_HELPER) : 0;
        Integer members = userRepository.countRoles(ERole.ROLE_MEMBER) != null ? userRepository.countRoles(ERole.ROLE_MEMBER) : 0;

        return Arrays.asList(new Integer[] {admins, supervisors, moderators, helpers, members});
    }


}
