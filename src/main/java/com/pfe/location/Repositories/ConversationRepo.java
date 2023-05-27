package com.pfe.location.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfe.location.Models.Conversation;

public interface ConversationRepo extends JpaRepository<Conversation,Long> {

    @Query("SELECT c FROM Conversation c JOIN c.persons p1 JOIN c.persons p2 WHERE p1.id_person = ?1 AND p2.id_person = ?2")
    Optional<Conversation> selectConvByPersonsIds(long id_sender , long id_receiver);   
    
    @Query("SELECT c FROM Conversation c JOIN c.persons p1 WHERE p1.id_person = ?1")
    List<Conversation> selectConvsByPersonId(long id_person); 
}
