package com.pfe.location.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.Apartment;
import com.pfe.location.Services.ApartmentService.ApartmentService;
import com.pfe.location.Services.ApartmentService.UpdateApartmentRequest;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/apartment")
@RequiredArgsConstructor
public class ApartmentController {
    final ApartmentService apartmentService;
    
    @GetMapping("/all")
    public ResponseEntity<MyResponse> getAllApartments(){
        List<Apartment> apartments = apartmentService.getAllApartments();
        if(apartments.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty!"));
        }
        return ResponseEntity.ok(new MyResponse(200,apartments));
    }

    @GetMapping("/get/{id_apartment}")
    public ResponseEntity<MyResponse> getApartmentById(@PathVariable("id_apartment") long id){
        Optional<Apartment> apartment = apartmentService.getApartmentById(id);
        if(apartment.isPresent()){
            return ResponseEntity.ok(new MyResponse(200,apartment.get()));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"Not Found!"));
    }

    @PostMapping("/add")
    public ResponseEntity<MyResponse> saveApartment(@RequestBody Apartment apartment){
        try{
            return ResponseEntity.ok(new MyResponse(200,apartmentService.saveApartment(apartment))); 
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id_apartment}")
    public ResponseEntity<MyResponse> deleteApartment(@PathVariable("id_apartment") long id){
        try{
            return apartmentService.deleteApartment(id);
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
        }
    }

    @PutMapping("/update/{id_apartment}")
    public ResponseEntity<MyResponse> updateApartment(@RequestBody UpdateApartmentRequest apartment , @PathVariable("id_apartment") long id){
        return apartmentService.updateApartment(apartment, id);
    }

    @GetMapping("/apartmentbyidperson/{id_person}")
    public ResponseEntity<?> getApartmentsByPersonId(@PathVariable("id_person") long id_person){
        List<Apartment> apartments = apartmentService.getApartmentsByPersonId(id_person);
        if(apartments.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(500,"empty list!")); 
        }
        return ResponseEntity.ok(apartments);
    }
    
}
