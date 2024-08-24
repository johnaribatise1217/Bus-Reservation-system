package com.reservation.bus_reservation.models;

import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer busId;
  private String busName;

  @Enumerated(EnumType.STRING)
  private BusType busType;

  private Integer seats;
  @Column(nullable = false, columnDefinition = "Integer default 0")
  private Integer availableSeats;
  
  private LocalTime departureTime;
  private LocalTime arrivalTime;
  
  @Column(nullable = false, columnDefinition = "Integer default 0")
  private Integer numberReservation;
  
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "route_id")
  private Route route;

  @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "bus")
  private List<Reservation> reservations;

  @PrePersist
  protected void onCreate(){
    if(this.availableSeats == null){
      this.availableSeats = seats;
      System.out.println(availableSeats);
    }
    if(this.numberReservation == null){
      this.numberReservation = seats - availableSeats;
      System.out.println(numberReservation);
    }
  }
}
