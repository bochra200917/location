package com.pfe.location.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pfe.location.Models.Complaint;
import com.pfe.location.Repositories.ComplaintRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintService {
    final ComplaintRepo complaintRepo ; 

    public List<Complaint> getAllComplaints(){
        return complaintRepo.findAll();
    }

    public Optional<Complaint> getComplaintById(long id){
        return complaintRepo.findById(id);
    }

    public void sendComplaint(Complaint complaint){
        complaint.setDate_complaint(LocalDate.now());
        complaintRepo.save(complaint);
    }
}
