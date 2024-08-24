package com.reservation.bus_reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.Reservation;
import com.reservation.bus_reservation.models.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

  @Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.bus = :bus")
  Optional<Reservation> findByUserAndByBus(User user, Bus bus);
}
