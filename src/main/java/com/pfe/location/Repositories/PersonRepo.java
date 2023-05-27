package com.pfe.location.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfe.location.Models.Person;

public interface PersonRepo extends JpaRepository<Person,Long> {
    Optional<Person> findPersonByEmail(String email);
    Optional<Person> findPersonByTelNumber(String tel);

    @Query("select p from Person p where p.user.username=?1")
    Optional<Person> findPersonByUsername(String username);

}
