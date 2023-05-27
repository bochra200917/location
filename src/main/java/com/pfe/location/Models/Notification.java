package com.pfe.location.Models;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long not_id ;

    private LocalDate not_date ; 

    @Enumerated(EnumType.STRING)
    private TypeNotification typeNotification ; 

    private int status ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_request")
    private Reservation reservation ;

    public Notification(LocalDate not_date, TypeNotification type, Reservation reservation) {
        this.not_date = not_date;
        this.typeNotification = type;
        this.reservation = reservation;
    }
    
}
