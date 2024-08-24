package com.reservation.bus_reservation.models.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {
  private String oldPassword;
  private String newPassword;
}
