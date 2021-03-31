package com.monitoring.documents.controllers;

import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test() {
        return "asdf";
    }

    @GetMapping(path = "/all")
    public List<UserEntity> getAllStudents()
    {
        return userService.getAllUsers();
    }


    @GetMapping(path = "{userId}")
    public Optional<UserEntity> getStudentById(@PathVariable("userId") Long studentId) {
        return userService.getUserById(studentId);
    }

    @PostMapping(path = "add")
    public void registerUser(@RequestBody UserEntity user) { userService.save(user);}

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("studentId") Long userId) { userService.deleteUser(userId);}

    @PutMapping(path = "{userId}")
    public void updateStudent(@PathVariable("userId") Long id, @RequestBody UserEntity userEntity) {
        userService.updateUser(id, userEntity);
    }



}
