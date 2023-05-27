package com.pfe.location.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.location.Models.User;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username); 
}
