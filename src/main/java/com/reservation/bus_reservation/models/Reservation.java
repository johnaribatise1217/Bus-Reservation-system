package com.reservation.bus_reservation.models;

import java.time.LocalDate;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private LocalDate reservationDate;
  private LocalTime reservationTime;
  private String busName;

  @Enumerated(EnumType.STRING)
  private BusType busType;

  private String userName;

  @Column(nullable = true)
  private int seatNo;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "bus_id")
  private Bus bus;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  private User user;

  @PrePersist
  protected void onCreate(){
    if(this.reservationDate == null){
      this.reservationDate = LocalDate.now();
    }
    if(this.reservationTime == null){
      this.reservationTime = LocalTime.now();
    }
  }
}
