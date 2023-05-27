package com.pfe.location.Services.ApartmentService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateApartmentRequest {
    private String state; //wileya
    private String city; //mdina
    private String street; // chera3
    private String apartment_number;  
    private String type ; 
    private float price ; 
    private String description ;  
    private float surface ; 
    private int s_count ;
    private double lng ; 
    private double lat ; 
}
