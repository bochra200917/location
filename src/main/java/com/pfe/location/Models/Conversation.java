package com.pfe.location.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_conv;

    private LocalDate date_conv;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persons_conversations", joinColumns = @JoinColumn(name = "id_conv", referencedColumnName = "id_conv"), inverseJoinColumns = @JoinColumn(name = "id_person", referencedColumnName = "id_person"))
    Collection<Person> persons = new ArrayList<Person>();

    @OneToMany(mappedBy = "conversation" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    Collection<Message> messages = new ArrayList<Message>();

    public Conversation(LocalDate date_conv, Collection<Person> persons, Collection<Message> messages) {
        this.date_conv = date_conv;
        this.persons = persons;
        this.messages = messages;
    }

}
