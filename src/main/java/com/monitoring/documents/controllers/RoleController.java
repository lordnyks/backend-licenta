package com.monitoring.documents.controllers;

import com.monitoring.documents.model.RoleEntity;
import com.monitoring.documents.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(path = "roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleEntity> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(path = "{id}")
    public Optional<RoleEntity> getRoleById(@PathVariable("id") Long roleId) {
        return roleService.getRoleById(roleId);
    }

}
