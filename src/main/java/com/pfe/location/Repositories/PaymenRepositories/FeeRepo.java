package com.pfe.location.Repositories.PaymenRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.Payment.Fee;

public interface FeeRepo extends JpaRepository<Fee , Long> {
    
}
