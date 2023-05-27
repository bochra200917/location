package com.pfe.location.Services.OpinionService;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Apartment;
import com.pfe.location.Models.Opinion;
import com.pfe.location.Repositories.ApartmentRepo;
import com.pfe.location.Repositories.OpinionRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OpinionService {
    final OpinionRepo opinionRepo ;
    final ApartmentRepo apartmentRepo;

    public void postOpinionInApartment(Opinion opinion, long id_apartment){
        Apartment apartment = apartmentRepo.findById(id_apartment)
                               .orElseThrow( ()-> new IllegalStateException("id not found!"));
        opinion.setOpinion_date(LocalDate.now());
        opinion.setApartment(apartment);
        apartment.getOpinions().add(opinion);       
        opinionRepo.save(opinion);
    }

    public ResponseEntity<MyResponse> deleteOpinion(long id){
        Optional<Opinion> opinion = opinionRepo.findById(id);
        if(opinion.isPresent()){
            opinionRepo.deleteById(id);
            return ResponseEntity.ok(new MyResponse(200,"deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"id not found!"));
    }

    public ResponseEntity<MyResponse> updateOpinion(long id, UpdateOpinionRequest opinion){
        Optional<Opinion> op = opinionRepo.findById(id);
        if(op.isPresent()){
            op.get().setContent(opinion.getContent());
            op.get().setOpinion_date(LocalDate.now());
            return ResponseEntity.ok(new MyResponse(200,"updated successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"id not found!"));
    }

    public ResponseEntity<?> getOpinionById(long id){
        Optional<Opinion> opinion = opinionRepo.findById(id);
        if(opinion.isPresent()){
            return ResponseEntity.ok(new MyResponse(200,opinion.get()));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"Opinion not found!"));
    }


}
