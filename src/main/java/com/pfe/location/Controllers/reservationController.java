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

import com.pfe.location.Models.Reservation;
import com.pfe.location.Services.ReservationService;
import com.pfe.location.Utils.MyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="/api/v1/rental")
@RequiredArgsConstructor
public class reservationController {
    final ReservationService reservationService ; 

    @PostMapping(path="/apartment/{id_apart}/person/{id_person}")
    public ResponseEntity<MyResponse> createReservaion(@RequestBody Reservation request,
    @PathVariable("id_apart") long id_apart , @PathVariable("id_person") long id_person ){
        try{
            return reservationService.createReservation(request, id_apart, id_person);
        }catch(Exception e){
            return ResponseEntity.status(500).body(new MyResponse(500,e.getMessage()));
        }
    }

    @PostMapping("/apartment/{id_apart}")
    public ResponseEntity<MyResponse> verifReservationPeriod(@RequestBody Reservation reservation, @PathVariable("id_apart") long id_apart){
        return reservationService.verifReservationPeriod(reservation, id_apart);
    }

    @PostMapping(path="/cancel/{id}")
    public ResponseEntity<MyResponse> cancelReservation(@PathVariable("id") long id){
        return reservationService.cancelReservation(id);
    }


    @GetMapping("/by_id/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable("id") long id){
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if(reservation.isPresent()){
            return ResponseEntity.ok(new MyResponse(200,reservation.get()));
        }
        return ResponseEntity.status(404).body(new MyResponse(404,"No reservation!"));
    } 

    @GetMapping("/all")
    public ResponseEntity<?> getAllRentalReservation(){
        List<Reservation> reservations = reservationService.getAllReservations();
        if(reservations.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty list!"));
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/sellerreservations/{id_person}")
    public ResponseEntity<?> getSellerRentalRequests(@PathVariable("id_person") long id){
        List<Reservation> res = reservationService.getSellerReservations(id);
        if(res.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty!"));
        }
        return ResponseEntity.ok(new MyResponse(200,res));
    }

    @GetMapping("/buyerreservations/{id_person}")
    public ResponseEntity<?> getReservationsByPersonId(@PathVariable("id_person") long id_person){
        List<Reservation> res = reservationService.getReservationsByPersonId(id_person);
        if(res.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty!"));
        }
        return ResponseEntity.ok(new MyResponse(200,res));
    }

    @GetMapping("/apartment-reservations/{id_apart}")
    public ResponseEntity<?> getApartmentReservations(@PathVariable("id_apart") long id_apart){
        List<Reservation> res = reservationService.getReservationsByApartmentId(id_apart);
        if(res.size() == 0){
            return ResponseEntity.status(404).body(new MyResponse(404,"Empty!"));
        }
        return ResponseEntity.ok(new MyResponse(200,res));
    }

}
