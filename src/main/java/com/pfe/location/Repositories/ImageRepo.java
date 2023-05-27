package com.pfe.location.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.Image;

public interface ImageRepo extends JpaRepository<Image, Long> {
    
}
