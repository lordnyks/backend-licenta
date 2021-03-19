package com.monitoring.documents.repository;

import com.monitoring.documents.model.CarTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<CarTable, Long> {
}