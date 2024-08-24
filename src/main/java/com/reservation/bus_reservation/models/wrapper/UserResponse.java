package com.reservation.bus_reservation.models.wrapper;

import java.util.List;

import com.reservation.bus_reservation.models.ReservationResponses.ReservationUser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
  private int totalReservation;
  private List<ReservationUser> reservations;
}
