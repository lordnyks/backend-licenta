package com.monitoring.documents.repository;

import com.monitoring.documents.model.SubscriptionHelper;
import com.monitoring.documents.model.DocumentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<DocumentModel, Long> {
    Optional<DocumentModel> findById(Long id);
    List<DocumentModel> findByPlateNumber(String plateNumber);
    List<DocumentModel> findAllByUserId(Long userid);
    List<DocumentModel> findAllByUserIdAndDescription(Long userid, String description);
    List<DocumentModel> findAllByEmail(String email);

    Boolean existsEmailByPlateNumber(String plateNumber);
    Boolean existsPlateNumberByEmail(String email);
    //select * from abonamente s left join notifications n on s.id = n.subscription_id  where data_expirarii = '2021-06-20' AND n.id is null;
    @Query("select s from DocumentModel s left join NotificationsEntity n on s.id = n.subscriptionId where s.expireDate = :dateNow AND n.id is null")
    List<DocumentModel> findAllByExpireDate(@Param("dateNow") LocalDate dateNow);

    @Query("select count(s) from DocumentModel s where s.description = :description")
    Integer countByType(@Param("description") String description);

    // select marca_masina,count(marca_masina) from abonamente s where marca_masina != 'Carte de identitate' group by marca_masina
    @Query("select s.made as made,count(s.made) as countmade from DocumentModel s where s.made != 'Carte de identitate' group by s.made")
    List<SubscriptionHelper> countAllMades();
}
