package com.monitoring.documents.repository;

import com.monitoring.documents.model.RoleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleTable, Long> {
}
