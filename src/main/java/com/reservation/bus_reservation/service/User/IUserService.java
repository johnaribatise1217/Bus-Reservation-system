package com.reservation.bus_reservation.service.User;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.reservation.bus_reservation.models.Auth.AuthenticationRequest;
import com.reservation.bus_reservation.models.Auth.AuthenticationResponse;
import com.reservation.bus_reservation.models.Auth.RegisterRequest;
import com.reservation.bus_reservation.models.Role;
import com.reservation.bus_reservation.models.wrapper.UserResponse;

public interface IUserService {
  public String createUser(RegisterRequest request);
  public AuthenticationResponse authenticate(AuthenticationRequest request);
  public List<UserResponse> getAllUsers();
  public UserResponse getUserDetails(Integer id);
  public List<UserResponse> getAllUsersByRole(Role role);
  public ResponseEntity<String> updatePassword(Integer userId, String oldPassword, String newPassword);
}
