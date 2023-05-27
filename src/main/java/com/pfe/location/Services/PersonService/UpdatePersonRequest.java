package com.pfe.location.Services.PersonService;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonRequest {
    private long id_person ; 
    private String first_name ; 
    private String last_name ;
    private LocalDate birthday ; 
    private String telNumber ; 
    private String address ; 
    private String email ; 
}
