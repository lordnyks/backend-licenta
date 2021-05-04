package com.monitoring.documents.repository;


import com.monitoring.documents.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findUserByEmail(String email);
    UserEntity findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
