package com.reservation.bus_reservation.models.wrapper;

import com.reservation.bus_reservation.models.BusType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusWithoutRoute {
  private Integer id;
  private String busName;

  @Enumerated(EnumType.STRING)
  private BusType busType;
  private int numberReservation;
}
