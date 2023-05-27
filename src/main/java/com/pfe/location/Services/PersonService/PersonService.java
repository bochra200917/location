package com.pfe.location.Services.PersonService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Person;
import com.pfe.location.Models.User;
import com.pfe.location.Repositories.PersonRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
    final PersonRepo personRepo;

    public Optional<Person> findPersonById(long id_person) {
        return personRepo.findById(id_person);
    }

    public List<Person> findAllPersons() {
        return personRepo.findAll();
    }

    @Transactional
    public ResponseEntity<?> updatePerson(UpdatePersonRequest request, long id_person) {
        Optional<Person> person = personRepo.findById(id_person);
        if (person.isPresent()) {
            Optional<Person> person1 = personRepo.findPersonByTelNumber(request.getTelNumber());
            if (person1.isPresent() && !person1.equals(person)) {
                return ResponseEntity.status(400).body(new MyResponse(400, "Phone number taken!"));
            }
            Optional<Person> person2 = personRepo.findPersonByEmail(request.getEmail());
            if (person2.isPresent() && !person2.equals(person)) {
                return ResponseEntity.status(400).body(new MyResponse(400, "Email taken!"));
            }
            person.get().setFirst_name(request.getFirst_name());
            person.get().setLast_name(request.getLast_name());
            person.get().setBirthday(request.getBirthday());
            person.get().setTelNumber(request.getTelNumber());
            person.get().setAddress(request.getAddress());
            person.get().setEmail(request.getEmail());
            return ResponseEntity.ok(new MyResponse(200, "Person updated successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "Person not found!"));
    }

    public ResponseEntity<?> getPersonByUsername(String username) {
        Optional<Person> person = personRepo.findPersonByUsername(username);
        if (person.isPresent()) {
            return ResponseEntity.ok(new MyResponse(200, person.get()));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "person not found!"));
    }

    @Transactional
    public void addPersonDescription(String aboutMe, long id_person) {
        personRepo.findById(id_person).get().setAboutMe(aboutMe);
    }
    
    @Transactional
    public String disableUser(long id_person) {
        User user = personRepo.findById(id_person).get().getUser();
        user.setEnabled(false);
        return "Account disabled!";
    }


    @Transactional
    public String enableUser(long id_person) {
        User user = personRepo.findById(id_person).get().getUser();
        user.setEnabled(true);
        return "Account enabled!";
    }

}
