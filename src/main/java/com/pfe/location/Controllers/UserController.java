package com.pfe.location.Controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.User;
import com.pfe.location.Repositories.UserRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/user")
@RequiredArgsConstructor
@Transactional
public class UserController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @PutMapping(path="/update/{id_user}")
    public ResponseEntity<MyResponse> updateUserData(@PathVariable("id_user") long id_user , @RequestBody User user){
        Optional<User> userr = userRepo.findById(id_user);
        if(userr.isPresent()){
            Optional<User> userr1 = userRepo.findUserByUsername(user.getUsername());
            if(userr1.isPresent() && !userr1.equals(userr)){
                return ResponseEntity.status(400).body(new MyResponse(400,"Username taken!"));
            }
            userr.get().setUsername(user.getUsername());
            userr.get().setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.ok(new MyResponse(200,"Updated successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"user not found!"));
    }
    
}
