package com.reservation.bus_reservation.models.wrapper;

import java.time.LocalTime;
import java.util.List;

import com.reservation.bus_reservation.models.BusType;
import com.reservation.bus_reservation.models.ReservationResponses.ReservationBus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class EditedBus {
  private Integer busId;
  private String busName;

  @Enumerated(EnumType.STRING)
  private BusType busType;
  private Integer seats;
  private Integer availableSeats;
  private LocalTime departureTime;
  private LocalTime arrivalTime;
  private int numberReservation;
  private RouteNoBus route;
  private List<ReservationBus> bookedReservations;

  public EditedBus(Integer busId, String busName, BusType busType, Integer seats, Integer availableSeats,
      LocalTime departureTime, LocalTime arrivalTime, int numberReservation, RouteNoBus route, 
        List<ReservationBus> reservationBus
      ) {
    this.busId = busId;
    this.busName = busName;
    this.busType = busType;
    this.seats = seats;
    this.availableSeats = availableSeats;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.numberReservation = numberReservation;
    this.route = route;
    this.bookedReservations = reservationBus;
  }
}
