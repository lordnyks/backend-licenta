package com.monitoring.documents.controllers;

import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public List<UserEntity> getUserByEmail(@RequestParam(required = false) String email) {
        if(email == null) {
            return userService.getAllUsers();
        } else {
            return userService.getUserByEmail(email);
        }
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_MODERATOR')")
    @PutMapping(path = "{id}")
    public void updateStudent(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
        userService.updateUser(id, userEntity);
    }
}
