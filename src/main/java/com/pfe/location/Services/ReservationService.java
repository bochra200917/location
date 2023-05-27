package com.pfe.location.Services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pfe.location.Models.Apartment;
import com.pfe.location.Models.Notification;
import com.pfe.location.Models.Person;
import com.pfe.location.Models.Reservation;
import com.pfe.location.Models.TypeNotification;
import com.pfe.location.Models.Payment.Fee;
import com.pfe.location.Models.Payment.Invoice;
import com.pfe.location.Repositories.ApartmentRepo;
import com.pfe.location.Repositories.NotificationRepo;
import com.pfe.location.Repositories.PersonRepo;
import com.pfe.location.Repositories.ReservationRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    final ReservationRepo reservationRepo;
    final ApartmentRepo apartmentRepo;
    final NotificationRepo notificationRepo;
    final PersonRepo personRepo;

    public void updateReservationStatus(List<Reservation> reservations) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getFinish_date().isBefore(LocalDate.now())
                    && reservations.get(i).getStatus() != 1) {
                reservations.get(i).setStatus(2);
            }
        }
    }

    public ResponseEntity<MyResponse> createReservation(Reservation reservation, long id_apart, long id_person) {
        // Get the person who made the reservation and the apartment he wants to rent
        Person person = personRepo.findById(id_person).get();
        Apartment apartment = apartmentRepo.findById(id_apart).get();
        // Create reservation
        reservation.setApartment(apartment);
        reservation.setPerson(person);
        reservation.setStatus(0);
        reservation.setReservation_date(LocalDate.now());
        // create transaction
        double total = ChronoUnit.DAYS.between(reservation.getStart_date(),
                reservation.getFinish_date()) * reservation.getApartment().getPrice();
        reservation.getTransaction().setTotal_price(total);
        reservation.getTransaction().setPayment_date(LocalDate.now());
        // Create fee
        Fee fee = new Fee();
        fee.setFee_amount(ChronoUnit.DAYS.between(reservation.getStart_date(),
                reservation.getFinish_date()) * reservation.getApartment().getPrice() * 0.1);
        fee.setFee_date(LocalDate.now());
        reservation.getTransaction().setFee(fee);
        // Create invoice
        Invoice invoice = new Invoice();
        invoice.setInvoice_date(LocalDate.now());
        reservation.getTransaction().setInvoice(invoice);
        // Send notification to apartment owner
        Notification notification = new Notification(LocalDate.now(), TypeNotification.REQUEST, reservation);
        // Save changes in database
        Reservation res = reservationRepo.save(reservation);
        notificationRepo.save(notification);
        return ResponseEntity.ok(new MyResponse(200, res));
    }

    public ResponseEntity<MyResponse> verifReservationPeriod(Reservation reservation, long id_apart) {
        // check if the inserted date is after the current date
        if (reservation.getStart_date().isBefore(LocalDate.now())) {
            return ResponseEntity.status(400).body(new MyResponse(400, "Can't do reservation!"));
        }
        // Check if the reservation period is free
        /*
         * List<Reservation> reservations =
         * reservationRepo.getReservationsByApartmentId(id_apart);
         * for(int i=0 ; i<reservations.size() ; i++){
         * if(reservation.getStart_date().isBefore(reservations.get(i).getFinish_date())
         * && reservations.get(i).getStatus()==0 ||
         * reservation.getFinish_date().isAfter(reservations.get(i).getStart_date()) &&
         * reservations.get(i).getStatus()==0){
         * return ResponseEntity.status(400).body(new
         * MyResponse(400,"Please change the reservation period!"));
         * }
         * }
         * return ResponseEntity.ok(new MyResponse(200,"Reservation is valid!"));
         */
        List<Reservation> res = reservationRepo.ifResExists(id_apart, reservation.getFinish_date(),
                reservation.getStart_date());
        if (res.size() == 0) {
            return ResponseEntity.ok(new MyResponse(200, "Reservation is valid!"));
        }
        return ResponseEntity.status(400).body(new MyResponse(400, "Please change the reservation period!"));
    }

    public ResponseEntity<MyResponse> cancelReservation(long id_res) {
        Optional<Reservation> res = reservationRepo.findById(id_res);
        if (res.isPresent()) {
            res.get().setStatus(1);
            Notification notification = new Notification(LocalDate.now(), TypeNotification.CANCEL, res.get());
            notificationRepo.save(notification);
            return ResponseEntity.ok(new MyResponse(200, "Done!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "There is no reservation!"));

    }

    public List<Reservation> getReservationsByPersonId(long id_person) {
        List<Reservation> reservations = reservationRepo.getReservationsByPersonId(id_person);
        updateReservationStatus(reservations);
        return reservations;
    }

    public Optional<Reservation> getReservationById(long id) {
        return reservationRepo.findById(id);
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        updateReservationStatus(reservations);
        return reservations;
    }

    public List<Reservation> getSellerReservations(long id_person) {
        List<Reservation> reservations = reservationRepo.getSellerReservations(id_person);
        updateReservationStatus(reservations);
        return reservations;
    }

    public List<Reservation> getReservationsByApartmentId(long id_apart) {
        List<Reservation> reservations = reservationRepo.getReservationsByApartmentId(id_apart);
        updateReservationStatus(reservations);
        return reservations.stream().filter(elem -> elem.getStatus() == 0).toList();
    }
}
