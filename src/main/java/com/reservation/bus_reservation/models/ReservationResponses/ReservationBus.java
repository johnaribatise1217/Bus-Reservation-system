package com.reservation.bus_reservation.models.ReservationResponses;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationBus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String userFirstName;
  private String userLastName;
  private LocalDate bookingDate;
  private LocalTime bookingTime;
}
