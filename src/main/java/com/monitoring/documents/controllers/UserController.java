package com.monitoring.documents.controllers;

import com.monitoring.documents.model.ERole;
import com.monitoring.documents.model.ResetPassword;
import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.services.EmailService;
import com.monitoring.documents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public List<UserEntity> getUserByEmail(@RequestParam(required = false) String email) {
        if(email == null) {
            return userService.getAllUsers();
        } else {
            return userService.getUserByEmail(email);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER')")
    @GetMapping(path = "allEmails")
    public List<String> getAllUsersByEmail() {
        return userService.getAllUsersByEmail();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR')")
    @GetMapping(path = "{id}")
    public Optional<UserEntity> getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR')")
    @PostMapping
    public void registerUser(@RequestBody UserEntity user) { userService.save(user);}

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR')")
    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") Long userId) { userService.deleteUser(userId);}

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_MEMBER')")
    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable("id") Long id, @Validated @RequestBody UserEntity userEntity) {
        userService.updateUser(id, userEntity);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @PatchMapping(path = "{email}")
    public void updateRole(@PathVariable("email") String email, @RequestParam String role, @RequestParam String asker ) {
        userService.updateRole(email, role, asker);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR', 'ROLE_HELPER', 'ROLE_MEMBER')")
    @GetMapping(path = "role")
    public ERole getRole(@RequestParam String email) {
        return userService.getRole(email);
    }

    @GetMapping(path = "/countUsers")
    public Optional<Integer> getAllUsers() {
        return userService.countAllUsers();
    }

    @GetMapping(path = "/countGenders")
    public List<Integer> getAllMaleUsers() {
        return userService.countGenders();
    }

    @GetMapping(path = "/countRoles")
    public List<Integer> getAllRoles() {
        return userService.getCountRoles();
    }

    @PatchMapping(path = "/sendTokenEmail") //sendTokenEmail?id=x;
    public void sendEmail(@RequestParam String email) {
        UserEntity userTemp = userService.getByEmail(email).orElseThrow(() -> new IllegalStateException("Utilizatorul cu email-ul " + email + " nu exista!"));

        ResetPassword password = new ResetPassword();

        userTemp.setResetToken(password.getToken());

        emailService.sendMessage(userTemp.getEmail(), "Resetare parolă", "Codul pentru resetare parolei este: " + userTemp.getResetToken());

        userService.save(userTemp);
    }

    @PatchMapping(path = "/resetPassword")
    public void resetPassword(@RequestParam String email, @RequestBody ResetPassword password) {
        UserEntity userTemp = userService.getByEmail(email).orElseThrow(() -> new IllegalStateException("Utilizatorul cu email-ul " + email + " nu exista!"));

        if(!bCryptPasswordEncoder.matches(password.getPassword(),userTemp.getPassword())) {
            userTemp.setPassword(bCryptPasswordEncoder.encode(password.getPassword()));
            emailService.sendMessage(userTemp.getEmail(), "Parola actualizată", String.format("Parola ta a fost actualizată în data de: %s.", LocalDate.now()));
            userTemp.setResetToken(null);
            userService.save(userTemp);
        } else {

            throw new IllegalStateException("Parola pe care ai inserat-o este aceeași cu cea prezentă.");
        }

    }


}
