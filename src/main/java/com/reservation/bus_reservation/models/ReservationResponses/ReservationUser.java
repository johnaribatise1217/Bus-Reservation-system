package com.reservation.bus_reservation.models.ReservationResponses;

import java.time.LocalDate;

import com.reservation.bus_reservation.models.BusType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private LocalDate reservationDate;
  private String busName;

  @Enumerated(EnumType.STRING)
  private BusType busType;
  private int seatNo;
}
