package com.monitoring.documents.services;

import com.monitoring.documents.model.RoleEntity;
import com.monitoring.documents.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<RoleEntity> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }
}
