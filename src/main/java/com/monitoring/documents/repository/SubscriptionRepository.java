package com.monitoring.documents.repository;

import com.monitoring.documents.model.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    Optional<Subscriptions> findById(Long id);
    List<Subscriptions> findAllByUserId(Long userid);
    List<Subscriptions> findAllByUserIdAndDescription(Long userid, String description);
    List<Subscriptions> findAllByEmail(String email);

    @Query("select s from Subscriptions s where s.expireDate = :dateNow")
    List<Subscriptions> findAllByExpireDate(@Param("dateNow") Date dateNow);

    List<Subscriptions> findByExpireDateBetween(Date date1, Date date2);

}
