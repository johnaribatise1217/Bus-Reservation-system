package com.reservation.bus_reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.bus_reservation.models.Auth.AuthenticationRequest;
import com.reservation.bus_reservation.models.Auth.AuthenticationResponse;
import com.reservation.bus_reservation.models.Auth.RegisterRequest;
import com.reservation.bus_reservation.models.Reservation;
import com.reservation.bus_reservation.models.Role;
import com.reservation.bus_reservation.models.wrapper.UpdatePasswordRequest;
import com.reservation.bus_reservation.models.wrapper.UserResponse;
import com.reservation.bus_reservation.service.ReservationService;
import com.reservation.bus_reservation.service.User.UserServiceImplementation;

@RestController
@RequestMapping("/api/auth/user")
public class UserController {
  @Autowired
  private UserServiceImplementation userServiceImplementation;
  @Autowired
  private ReservationService reservationService;

  @PostMapping("/register")
  public String registerUser(@RequestBody RegisterRequest registerRequest){
    try {
      return userServiceImplementation.createUser(registerRequest);
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest){
    try {
      return new ResponseEntity<>(userServiceImplementation.authenticate(authenticationRequest), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/book-reservation")
  @PreAuthorize("hasAuthority('PASSENGER')")
  public ResponseEntity<String> bookReservation(@RequestBody Reservation reservation){
    try {
      return reservationService.createReservation(reservation);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/get-all-users")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<UserResponse>> getAllUsers(){
    try {
      return ResponseEntity.status(HttpStatus.OK).body(userServiceImplementation.getAllUsers());
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get-users-byrole")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<UserResponse>> getAllUsersByRole(@RequestParam("role") Role role){
    try {
      return ResponseEntity.status(HttpStatus.FOUND).body(userServiceImplementation.getAllUsersByRole(role));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/get/{id}")
  @PreAuthorize("hasAuthority('PASSENGER')")
  public ResponseEntity<UserResponse> getUserDetails(@PathVariable("id") Integer id){
    try {
      return ResponseEntity.status(HttpStatus.FOUND).body(userServiceImplementation.getUserDetails(id));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/update-password/{userId}")
  @PreAuthorize("hasAuthority('PASSENGER') OR hasAuthority('ADMIN')")
  public ResponseEntity<String> updateUserPassword(@PathVariable("userId") Integer userId, @RequestBody UpdatePasswordRequest request){
    try {
      return userServiceImplementation.updatePassword(userId, request.getOldPassword(), request.getNewPassword());
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
