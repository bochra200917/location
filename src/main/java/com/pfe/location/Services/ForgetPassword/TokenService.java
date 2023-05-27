package com.pfe.location.Services.ForgetPassword;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Person;
import com.pfe.location.Models.Token;
import com.pfe.location.Models.User;
import com.pfe.location.Repositories.PersonRepo;
import com.pfe.location.Repositories.TokenRepo;
import com.pfe.location.Utils.EmailService;
import com.pfe.location.Utils.MyResponse;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    final PersonRepo personRepo ; 
    final EmailService emailService ; 
    final PasswordEncoder passwordEncoder;
    final TokenRepo tokenRepo ; 


    public String getRandomUniqueToken(){
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Date getTokenExpirationDate(){
        return new Date(System.currentTimeMillis() + 1000 * 60 * 10);
    }

    public ResponseEntity<MyResponse> sendToken(EmailObject email){
        Optional<Person> person = personRepo.findPersonByEmail(email.getEmail());
        //System.out.println(person.toString());
        if(person.isPresent()){
            Token tok = new Token(getRandomUniqueToken(),getTokenExpirationDate());
            User user = person.get().getUser();
            user.setToken(tok);
            tokenRepo.save(tok);
            try {
                emailService.sendEmail(person.get().getEmail(), "Reset_Password", "Here is your reset token: "+tok.getToken_text());
            } catch (MessagingException e) {
                return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
            }
            return ResponseEntity.ok(new MyResponse(200,"Token sent!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"Email not found!"));
    }

    public ResponseEntity<MyResponse> verifToken(VerifTokenRequest request){
        Person person = personRepo.findPersonByEmail(request.getEmail()).get();
        Date dateNow = new Date(System.currentTimeMillis()); 
        Date expDateCurrentPerson = person.getUser().getToken().getExp_date();
        int result = dateNow.compareTo(expDateCurrentPerson);
        if(result > 0){
            return ResponseEntity.status(401).body(new MyResponse(401,"Token expired!"));
        }
        if(!request.getToken().equals(person.getUser().getToken().getToken_text())){
            return ResponseEntity.status(401).body(new MyResponse(401,"Invalid token!"));
        }
        return ResponseEntity.ok(new MyResponse(200,"Token is valid!"));
    }

    public ResponseEntity<MyResponse> resetPassword(ResetPasswordRequest request){
        Person person = personRepo.findPersonByEmail(request.getEmail()).get();
        
        if(request.getPassword().equals(request.getConfirmPassword()) && 
           request.getToken().equals(person.getUser().getToken().getToken_text())){
            
            person.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
            tokenRepo.deleteById(person.getUser().getToken().getId_token());
            person.getUser().setToken(null);
            return ResponseEntity.ok(new MyResponse(200,"Password changed successfully!"));
        }
        return ResponseEntity.status(401).body(new MyResponse(401,"Something went wrong!"));
    }


    
}
