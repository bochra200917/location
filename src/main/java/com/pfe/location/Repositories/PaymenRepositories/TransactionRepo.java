package com.pfe.location.Repositories.PaymenRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.Payment.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction , Long> {
    
}
