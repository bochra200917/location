package com.pfe.location.Models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_token ;
    
    @Column(length = 500) 
    private String token_text ; 
    private Date exp_date ; 
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER , mappedBy = "token")
    User user ;

    public Token(String token_text, Date exp_date) {
        this.token_text = token_text;
        this.exp_date = exp_date;
    } 

    
    
}
