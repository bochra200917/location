package com.pfe.location.Services.auth;

import java.time.LocalDate;

import com.pfe.location.Models.Image;
import com.pfe.location.Models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String first_name ; 
    private String last_name ;
    private LocalDate birthday ; 
    private String tel_number ; 
    private String address ; 
    private String email ; 
    private Image image ;  
    private User user ; 

 
}
