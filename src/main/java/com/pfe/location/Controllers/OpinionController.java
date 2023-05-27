package com.pfe.location.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.Opinion;
import com.pfe.location.Services.OpinionService.OpinionService;
import com.pfe.location.Services.OpinionService.UpdateOpinionRequest;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/opinion")
@RequiredArgsConstructor
public class OpinionController {
    final OpinionService opinionService;
    
    @PostMapping("/add/{apart_id}")
    public ResponseEntity<MyResponse> addOpinion(@RequestBody Opinion opinion ,@PathVariable("apart_id") long id){
        try{
            opinionService.postOpinionInApartment(opinion , id);
            return ResponseEntity.ok(new MyResponse(200,"inserted successfully!"));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MyResponse> deleteOpinion(@PathVariable("id") long id){
        try{
            return opinionService.deleteOpinion(id);
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MyResponse> updateOpinion(@PathVariable("id") long id , @RequestBody UpdateOpinionRequest opinion){
        return opinionService.updateOpinion(id , opinion);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<?> findOpinionById(@PathVariable("id") long id){
        return opinionService.getOpinionById(id);
    }
    
}
