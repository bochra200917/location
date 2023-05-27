package com.pfe.location.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.MsgNotification;
import com.pfe.location.Models.Notification;
import com.pfe.location.Repositories.MsgNotificationRepo;
import com.pfe.location.Services.NotificationService;
import com.pfe.location.Utils.MyResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    final NotificationService notificationService;
    final MsgNotificationRepo msgNotificationRepo;

    @GetMapping("/buyer/{id}")
    public ResponseEntity<?> getBuyerNotifications(@PathVariable("id") long id) {
        try {
            List<Notification> notifications = notificationService.getBuyerNotifications(id);
            if (notifications.size() == 0) {
                return ResponseEntity.status(404).body(new MyResponse(404, "Empty!"));
            }
            return ResponseEntity.ok(new MyResponse(200, notifications));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(new MyResponse(403, e.getMessage()));
            // => because the return value of
            // "com.pfe.location.Models.Notification.getTypeNotification()" is null
        }
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<?> getSellerNotifications(@PathVariable("id") long id) {
        try {
            List<Notification> notifications = notificationService.getSellerNotifications(id);
            if (notifications.size() == 0) {
                return ResponseEntity.status(404).body(new MyResponse(404, "Empty!"));
            }
            return ResponseEntity.ok(new MyResponse(200, notifications));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(new MyResponse(403, e.getMessage()));
            // => because the return value of
            // "com.pfe.location.Models.Notification.getTypeNotification()" is null
        }
    }

    @PostMapping("/msg_notification")
    public void sendMsgNotification(@RequestBody MsgNotification not) {
        Boolean verif = msgNotificationRepo
                .findAll()
                .stream()
                .anyMatch(elem -> elem.getId_conv() == not.getId_conv()
                        && elem.getReceiver_username().equals(not.getReceiver_username()));
        if (!verif) {
            not.setStatus(0);
            msgNotificationRepo.save(not);
        }
    }

    @GetMapping("/msg_notifications/{username}")
    public long getMsgNotByUsername(@PathVariable("username") String username) {
        return msgNotificationRepo
                .findAll()
                .stream()
                .filter(elem -> elem.getReceiver_username().equals(username) && elem.getStatus() == 0)
                .count();
    }

    @PutMapping("/msg_notifications/update")
    public void updateMsgNot(@RequestBody Update update){
        msgNotificationRepo.findAll().stream().forEach(elem ->{
            if(elem.getId_conv() == update.getId_conv() && elem.getReceiver_username().equals(update.getUsername())){
              msgNotificationRepo.deleteById(elem.getId());
            }
        });
    }

    @PutMapping("/update/{id_not}")
    public ResponseEntity<?> updateNotificationStatus(@PathVariable("id_not") long id_not) {
        return notificationService.updateNotificationStatus(id_not);
    }
}

@Data
class Update {
    private long id_conv;
    private String username;
}
