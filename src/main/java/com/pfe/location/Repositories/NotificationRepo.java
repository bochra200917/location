package com.pfe.location.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfe.location.Models.Notification;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
    
    @Query("select n from Notification n where n.reservation.person.id_person = ?1")
    List<Notification> getBuyerNotifications(long id);

    @Query("select n from Notification n where n.reservation.apartment.person.id_person = ?1")
    List<Notification> getSellerNotifications(long id);
}
