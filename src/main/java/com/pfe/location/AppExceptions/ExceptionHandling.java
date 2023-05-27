package com.pfe.location.AppExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pfe.location.Utils.MyResponse;



@RestControllerAdvice
public class ExceptionHandling {
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MyResponse> handleArgument(HttpMessageNotReadableException ex){
        return ResponseEntity.status(400).body(new MyResponse(400,ex.getMessage()));
    }

    /*@ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<MyResponse> wrong(BadCredentialsException ex){
        return ResponseEntity.status(403).body(new MyResponse(403,"Wrong username or password!"));
    }*/

    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MyResponse> wronglink(Exception ex){
        return ResponseEntity.status(401).body(new MyResponse(401,ex.getMessage()));
    }*/

}
