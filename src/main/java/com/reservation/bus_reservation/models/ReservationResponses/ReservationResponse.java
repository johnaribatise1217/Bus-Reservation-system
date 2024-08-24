package com.reservation.bus_reservation.models.ReservationResponses;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservation.bus_reservation.models.BusType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private LocalTime time;
  private LocalDate date;
  private String username;
  private BusType busType;
  private String busName;
}
