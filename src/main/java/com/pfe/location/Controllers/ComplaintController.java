package com.pfe.location.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.location.Models.Complaint;
import com.pfe.location.Services.ComplaintService;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/Api/v1/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    final ComplaintService complaintService ; 
    
    @GetMapping(path="/all")
    public ResponseEntity<?> getAllComplaints(){
        List<Complaint> complaints = complaintService.getAllComplaints();
        if(complaints.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty list"));
        }
        return ResponseEntity.ok(new MyResponse(200,complaints));
    }
    
    @GetMapping(path="/byid/{id}")
    public ResponseEntity<?> getComplaintById(@PathVariable("id") long id){
        Optional<Complaint> complaint = complaintService.getComplaintById(id);
        if(complaint.isPresent()){
            return ResponseEntity.ok(new MyResponse(200,complaint));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"Complaint not found!"));
    }
    
    @PostMapping(path="/send")
    public ResponseEntity<MyResponse> sendComplaint(@RequestBody Complaint complaint){
        try{
            complaintService.sendComplaint(complaint);
            return ResponseEntity.ok(new MyResponse(200,"ok!"));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,"Something went wrong!"));
        }
    }
    
}
