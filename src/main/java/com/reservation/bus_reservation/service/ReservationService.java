package com.reservation.bus_reservation.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.Reservation;
import com.reservation.bus_reservation.models.User;
import com.reservation.bus_reservation.repository.BusRepository;
import com.reservation.bus_reservation.repository.ReservationRepository;
import com.reservation.bus_reservation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
  @Autowired
  private final ReservationRepository reservationRepository;
  @Autowired
  private final BusRepository busRepository;
  @Autowired
  private final UserRepository userRepository;
  private LocalTime time = LocalTime.now();

  public ResponseEntity<String> createReservation(Reservation reservation){
    Optional<Bus> busOptional = busRepository.findBusByBusNameAndBusType(reservation.getBusName(), reservation.getBusType());
    if (busOptional.isEmpty()) {
      return new ResponseEntity<>("Bus not found", HttpStatus.NOT_FOUND);
    }
    Bus bus = busOptional.get();

    Optional<User> userOptional = userRepository.findByEmail(reservation.getUserName());
    if (userOptional.isEmpty()) {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    User user = userOptional.get();

    //check if the user has already made a reservation for thus bus
    Optional<Reservation> existingReservation = reservationRepository.findByUserAndByBus(user, bus);
    if(existingReservation.isPresent()){
      return new ResponseEntity<>("You cannot book another reservation for this bus", HttpStatus.BAD_REQUEST);
    }

    synchronized(bus) {
      if(time.isAfter(bus.getDepartureTime()) && time.equals(bus.getDepartureTime())){
        return new ResponseEntity<>("Oops, the bus just boarded , reservations have been shutdown!", HttpStatus.BAD_REQUEST);
      } 

      if(bus.getAvailableSeats() == 0){
        return new ResponseEntity<>("There are no available seats for this bus", HttpStatus.BAD_REQUEST);
      }
      
      int seatNo = generateUniqueSeatNo(bus);
      if(seatNo == -1){
        return new ResponseEntity<>("Failed to generate user seat number", HttpStatus.BAD_REQUEST);
      }

      bus.setAvailableSeats(bus.getAvailableSeats() - 1);
      bus.setNumberReservation(bus.getNumberReservation() + 1);
      busRepository.save(bus);

      reservation.setUser(user);
      reservation.setBus(bus);
      reservation.setSeatNo(seatNo);
      reservation.setBusType(bus.getBusType());
      reservationRepository.save(reservation);

      user.setTotalReservation(user.getReservations().size());
      userRepository.save(user);

      return new ResponseEntity<>("Your reservation has been created successfully", HttpStatus.CREATED);
    }
  }

  private int generateUniqueSeatNo(Bus bus){
    //get all reservations from the reservation repo
    List<Reservation> reservations = reservationRepository.findAll();
    Set<Integer> occupiedSeats = reservations.stream()
            .filter(reservation -> reservation.getBus().equals(bus))
            .map(Reservation::getSeatNo)
            .collect(Collectors.toSet());

    //loop through all the occupied seats 
    for(int i = 1; i <= bus.getSeats(); i++){
      if(!occupiedSeats.contains(i)){
        return i;
      }
    }

    return -1;
  }
}
