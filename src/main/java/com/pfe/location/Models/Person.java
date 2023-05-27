package com.pfe.location.Models;
import java.time.LocalDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_person ; 
    private String first_name ; 
    private String last_name ;
    private LocalDate birthday ; 
    private String telNumber ; 
    private String address ; 
    private String email ; 
    @Column(length = 2000)  
    private String aboutMe ;
    
    @OneToOne(fetch = FetchType.EAGER , mappedBy = "person" , cascade = CascadeType.ALL)
    User user ; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="image_id")
    private Image image ; 
}
