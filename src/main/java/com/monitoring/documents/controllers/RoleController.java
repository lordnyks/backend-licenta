package com.monitoring.documents.controllers;

import com.monitoring.documents.model.RoleEntity;
import com.monitoring.documents.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping(path = "all")
    public List<RoleEntity> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(path = "{roleId}")
    public Optional<RoleEntity> getRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.getRoleById(roleId);
    }

}
