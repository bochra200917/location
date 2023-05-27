package com.pfe.location.Models;

import java.time.LocalDate;

import com.pfe.location.Models.Payment.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_request ; 
    private LocalDate reservation_date; 
    private LocalDate start_date ; 
    private LocalDate finish_date ;
    @Column(length = 2000) 
    private String description ;
    private int status ; 
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_person")
    private Person person ; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_apartment")
    private Apartment apartment ; 
    
   @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
   @JoinColumn(name="id_transaction")
   private Transaction transaction ; 

   
}
