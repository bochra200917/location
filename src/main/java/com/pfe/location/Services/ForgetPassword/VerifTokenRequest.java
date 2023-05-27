package com.pfe.location.Services.ForgetPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifTokenRequest {
    private String token ; 
    private String email ; 
}
