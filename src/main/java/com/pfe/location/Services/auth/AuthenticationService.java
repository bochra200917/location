package com.pfe.location.Services.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Person;
import com.pfe.location.Models.Role;
import com.pfe.location.Models.User;
import com.pfe.location.Repositories.PersonRepo;
import com.pfe.location.Repositories.UserRepo;
import com.pfe.location.Security.JwtService;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
  private final PersonRepo personRepo;
  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<?> register(Person request) {
    Optional<Person> person1 = personRepo.findPersonByTelNumber(request.getTelNumber());
    if (person1.isPresent()) {
      return ResponseEntity.status(500).body("Phone number taken!");
    }
    Optional<Person> person2 = personRepo.findPersonByEmail(request.getEmail());
    if (person2.isPresent()) {
      return ResponseEntity.status(500).body("Email taken !");
    }
    Optional<User> userr = userRepo.findUserByUsername(request.getUser().getUsername());
    if (userr.isPresent()) {
      return ResponseEntity.status(500).body("Username taken !");
    }
    var user = User.builder()
        .username(request.getUser().getUsername())
        .password(passwordEncoder.encode(request.getUser().getPassword()))
        .role(Role.NORMAL_USER)
        .enabled(true)
        .build();
    request.setUser(user);
    user.setPerson(request);
    userRepo.save(user);
    personRepo.save(request);

    var jwtToken = jwtService.generateToken(user);
    return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
  }

  public ResponseEntity<?> authenticate(AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(),
              request.getPassword()));

    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(403).body(new MyResponse(403, "Wrong username or password!"));
    }

    var user = userRepo.findUserByUsername(request.getUsername())
        .orElseThrow(() -> new IllegalStateException("Something went wrong!"));
    var jwtToken = jwtService.generateToken(user);
    return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
  }

  public ResponseEntity<?> addAdmin(Person request) {
    Optional<Person> person1 = personRepo.findPersonByTelNumber(request.getTelNumber());
    if (person1.isPresent()) {
      return ResponseEntity.status(500).body("Phone number taken!");
    }
    Optional<Person> person2 = personRepo.findPersonByEmail(request.getEmail());
    if (person2.isPresent()) {
      return ResponseEntity.status(500).body("Email taken !");
    }
    Optional<User> userr = userRepo.findUserByUsername(request.getUser().getUsername());
    if (userr.isPresent()) {
      return ResponseEntity.status(500).body("Username taken !");
    }
    var user = User.builder()
        .username(request.getUser().getUsername())
        .password(passwordEncoder.encode(request.getUser().getPassword()))
        .role(Role.ROLE_ADMIN)
        .enabled(true)
        .build();
    request.setUser(user);
    user.setPerson(request);
    userRepo.save(user);
    personRepo.save(request);

    var jwtToken = jwtService.generateToken(user);
    return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
  }

}
