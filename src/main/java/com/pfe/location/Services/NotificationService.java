package com.pfe.location.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Notification;
import com.pfe.location.Models.TypeNotification;
import com.pfe.location.Repositories.NotificationRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    final NotificationRepo notificationRepo;

    public List<Notification> getBuyerNotifications(long id){
        List<Notification> notifications = notificationRepo.getBuyerNotifications(id);
        List<Notification> myNotifications = new ArrayList<Notification>(); 

        for(int i=0 ; i<notifications.size() ; i++){
            if(!notifications.get(i).getTypeNotification().equals(TypeNotification.REQUEST)){
                myNotifications.add(notifications.get(i));
            }
        }

        return myNotifications ; 
    }

    public List<Notification> getSellerNotifications(long id){
        List<Notification> notifications = notificationRepo.getSellerNotifications(id);
        List<Notification> myNotifications = new ArrayList<Notification>(); 

        for(int i=0 ; i<notifications.size() ; i++){
            if(notifications.get(i).getTypeNotification().equals(TypeNotification.REQUEST) ||
            notifications.get(i).getTypeNotification().equals(TypeNotification.CANCEL)){
                myNotifications.add(notifications.get(i));
            }
        }

        return myNotifications ; 
    }

    public ResponseEntity<MyResponse> updateNotificationStatus(long id){
        Optional<Notification> not = notificationRepo.findById(id);
        if(not.isPresent()){
            not.get().setStatus(1);
            return ResponseEntity.ok(new MyResponse(200,"updated successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"Notification not found!"));
    }
}
