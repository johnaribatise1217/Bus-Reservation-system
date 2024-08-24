package com.reservation.bus_reservation.models.Auth;

import com.reservation.bus_reservation.models.wrapper.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthenticationResponse {
  private String token;
  private UserResponse user;
}
