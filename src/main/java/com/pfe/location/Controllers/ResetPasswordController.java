package com.pfe.location.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Services.ForgetPassword.EmailObject;
import com.pfe.location.Services.ForgetPassword.ResetPasswordRequest;
import com.pfe.location.Services.ForgetPassword.TokenService;
import com.pfe.location.Services.ForgetPassword.VerifTokenRequest;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/reset_password")
@RequiredArgsConstructor
public class ResetPasswordController {
    final TokenService tokenService ; 

    @PostMapping(path="/send_token")
    public ResponseEntity<MyResponse> sendToken(@RequestBody EmailObject email){
        return tokenService.sendToken(email);
    }

    @PostMapping(path="/verif_token")
    public ResponseEntity<MyResponse> sendToken(@RequestBody VerifTokenRequest request){
        return tokenService.verifToken(request);
    }

    @PostMapping(path="/change_password")
    public ResponseEntity<MyResponse> sendToken(@RequestBody ResetPasswordRequest request){
        return tokenService.resetPassword(request);
    }


}
