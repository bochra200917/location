package com.pfe.location.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfe.location.Models.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.person.id_person = ?1")
    List<Reservation> getReservationsByPersonId(long id_person);

    @Query("select r from Reservation r where r.apartment.person.id_person =?1")
    List<Reservation> getSellerReservations(long id_person);

    @Query("select r from Reservation r where r.apartment.id_apartment=?1")
    List<Reservation> getReservationsByApartmentId(long id_apartment);

    @Query("SELECT r FROM Reservation r WHERE r.apartment.id_apartment = :id_apart AND ((r.start_date < :finish_date AND r.finish_date > :start_date) OR (r.start_date >= :start_date AND r.start_date < :finish_date) OR(r.finish_date > :start_date AND r.finish_date <= :finish_date)) AND r.status = 0")
    List<Reservation> ifResExists(long id_apart , LocalDate finish_date , LocalDate start_date);
}
