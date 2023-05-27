package com.pfe.location.Models;

import java.util.ArrayList;
import java.util.Collection;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_apartment; 
    private String state; //wileya
    private String city; //mdina
    private String street; // chera3
    private String apartment_number;  
    private String type ; 
    private float price ; 
    
    @Column(length = 2000)
    private String description ; 
    
    private float surface ; 
    private int s_count ;   
    private int status;  
    private double lng ;
    private double lat ;

    @OneToMany(fetch = FetchType.EAGER , mappedBy = "apartment")
    Collection<Opinion> opinions = new ArrayList<Opinion>(); 


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_person")
    private Person person; 

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL , mappedBy = "apartment")
    Collection<Image> images = new ArrayList<Image>(); 
}
