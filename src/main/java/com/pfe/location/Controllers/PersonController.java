package com.pfe.location.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.Person;
import com.pfe.location.Services.PersonService.PersonService;
import com.pfe.location.Services.PersonService.UpdatePersonRequest;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/person")
@RequiredArgsConstructor
public class PersonController {
    final PersonService personService;

    @GetMapping(path = "/byid/{id_person}")
    public ResponseEntity<?> findPersonById(@PathVariable("id_person") long id_person) {
        Optional<Person> person = personService.findPersonById(id_person);
        if (person.isPresent()) {
            return ResponseEntity.ok(new MyResponse(200, person.get()));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "Person not found!"));
    }

    @GetMapping(path = "/all")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllPersons() {
        List<Person> persons = personService.findAllPersons();
        if (persons.size() == 0) {
            return ResponseEntity.status(404).body(new MyResponse(404, "Empty list!"));
        }
        return ResponseEntity.ok(new MyResponse(200, persons));
    }

    @PutMapping(path = "/update/{id_person}")
    public ResponseEntity<?> updatePerson(@RequestBody UpdatePersonRequest request,
            @PathVariable("id_person") long id_person) {
        return personService.updatePerson(request, id_person);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getPersonByUsername(@PathVariable("username") String username) {
        return personService.getPersonByUsername(username);
    }

    @PostMapping("/aboutme/{id_person}")
    public ResponseEntity<MyResponse> addPersonDescription(@RequestBody String aboutMe,
            @PathVariable("id_person") long id_person) {
        try {
            personService.addPersonDescription(aboutMe, id_person);
            return ResponseEntity.ok(new MyResponse(200, "about me added successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MyResponse(500, e.getMessage()));
        }
    }

    @PutMapping(path = "/disable/{id_person}")
    public ResponseEntity<MyResponse> disableUser(@PathVariable("id_person") long id_person) {
        return ResponseEntity.ok(new MyResponse(200, personService.disableUser(id_person)));
    }

    @PutMapping(path = "/enable/{id_person}")
    public ResponseEntity<MyResponse> enableUser(@PathVariable("id_person") long id_person) {
        return ResponseEntity.ok(new MyResponse(200, personService.enableUser(id_person)));
    }
}
