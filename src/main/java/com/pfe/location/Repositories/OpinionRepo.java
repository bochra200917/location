package com.pfe.location.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.Opinion;

public interface OpinionRepo extends JpaRepository<Opinion,Long> {
    
}
