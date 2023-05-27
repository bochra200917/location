package com.pfe.location.Services.ForgetPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    private String email ; 
    private String token ; 
    private String password ; 
    private String confirmPassword ; 
}
