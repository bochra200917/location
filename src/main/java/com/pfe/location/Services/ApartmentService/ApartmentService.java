package com.pfe.location.Services.ApartmentService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Apartment;
import com.pfe.location.Repositories.ApartmentRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApartmentService {
    final ApartmentRepo apartmentRepo;

    public List<Apartment> getAllApartments() {
        return apartmentRepo.findAll().stream().filter(elem->elem.getStatus() == 0).toList();
    }

    public Optional<Apartment> getApartmentById(long id) {
        return apartmentRepo.findById(id);
    }

    public Apartment saveApartment(Apartment apartment) {
        return apartmentRepo.save(apartment);
    }

    public ResponseEntity<MyResponse> deleteApartment(long id) {
        if (apartmentRepo.existsById(id)) {
            apartmentRepo.findById(id).get().setStatus(1);
            return ResponseEntity.ok(new MyResponse(200, "Deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "Not found!"));
    }

    public ResponseEntity<MyResponse> updateApartment(UpdateApartmentRequest request, long id) {
        Optional<Apartment> apartment = apartmentRepo.findById(id);
        if (apartment.isPresent()) {
            Apartment apart = apartment.get();
            apart.setState(request.getState());
            apart.setCity(request.getCity());
            apart.setStreet(request.getStreet());
            apart.setApartment_number(request.getApartment_number());
            apart.setType(request.getType());
            apart.setPrice(request.getPrice());
            apart.setDescription(request.getDescription());
            apart.setSurface(request.getSurface());
            apart.setS_count(request.getS_count());
            apart.setLng(request.getLng());
            apart.setLat(request.getLat());
            return ResponseEntity.ok(new MyResponse(200, "Updated successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "Id not found!"));

    }

    public List<Apartment> getApartmentsByPersonId(long id_person) {
        return apartmentRepo.getApartmentsByPersonId(id_person);
    }

}
