package com.pfe.location.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.Complaint;

public interface ComplaintRepo extends JpaRepository<Complaint, Long> {
    
}
