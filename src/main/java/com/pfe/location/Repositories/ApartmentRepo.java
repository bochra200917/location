package com.pfe.location.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfe.location.Models.Apartment;

public interface ApartmentRepo extends JpaRepository<Apartment , Long> {
    
    @Query("select a from Apartment a where a.person.id_person =?1 and a.status = 0")
    List<Apartment> getApartmentsByPersonId(long id_person);    
}
