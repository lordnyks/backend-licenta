package com.monitoring.documents.repository;


import com.monitoring.documents.model.ERole;
import com.monitoring.documents.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findUsersByEmail(String email);

    @Query("select user.email from UserEntity user")
    List<String> getAllEmails();

    UserEntity findUserByUsername(String username);
    Optional<UserEntity> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("select count(user.id) from UserEntity user")
    Optional<Integer> countById();

    @Query("select count(user.profile.gender) from UserEntity user where user.profile.gender = :gender")
    Optional<Integer> countGenders(@Param("gender") String gender);

    @Query("select count(user.role) from UserEntity user where user.role = :role")
    Integer countRoles(@Param("role") ERole role);
}



