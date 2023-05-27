package com.pfe.location.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.Person;
import com.pfe.location.Services.auth.AuthenticationRequest;
import com.pfe.location.Services.auth.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Person request) {
      return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
      return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/add_admin")
    public ResponseEntity<?> addAdmin(@RequestBody Person person){
      return ResponseEntity.ok(service.addAdmin(person));
    }
}
