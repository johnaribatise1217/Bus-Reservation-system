package com.reservation.bus_reservation.models.Auth;

import com.reservation.bus_reservation.models.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  
  @Enumerated(EnumType.STRING)
  private Role role;
}
