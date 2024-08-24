package com.reservation.bus_reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.wrapper.EditedBus;
import com.reservation.bus_reservation.service.Bus.BusServiceImplementation;

@RestController
@RequestMapping("/api/bus")
public class BusController {
  @Autowired
  private BusServiceImplementation busServiceImpl;

  @PostMapping("/addbus")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> addBus(@RequestBody Bus bus){
    try {
      busServiceImpl.addBus(bus);
      return ResponseEntity.status(HttpStatus.CREATED).body("Bus Created successfully.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        e.getMessage()
      );
    }
  }

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<EditedBus>> getAllBus(){
    List<EditedBus> buses = busServiceImpl.viewAllBus();
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(buses);
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('BUS_DRIVER')")
  private ResponseEntity<EditedBus> getBusById(@PathVariable("id") Integer id){
    return ResponseEntity.status(HttpStatus.FOUND).body( busServiceImpl.viewBus(id));
  }

  @PutMapping("update-bus/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> updateBus(@PathVariable("id") Integer id, @RequestBody Bus bus){
    try {
      busServiceImpl.updateBusDetails(bus, id);
      return ResponseEntity.status(HttpStatus.OK).body("Bus updated Successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        e.getMessage()
      );
    }
  }

  @DeleteMapping("delete-bus/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteBus(@PathVariable("id") Integer id){
    try {
      busServiceImpl.deleteBus(id);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body("Bus deleted successfully");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        e.getMessage()
      );
    }
  }

  @DeleteMapping("delete-all")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteAllBuses(){
    try {
      busServiceImpl.deleteAllBuses();
      return ResponseEntity.status(HttpStatus.ACCEPTED).body("All Buses deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        e.getMessage()
      );
    }
  }
}