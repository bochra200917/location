package com.pfe.location.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.Token;

public interface TokenRepo extends JpaRepository<Token,Long> {
    
}
