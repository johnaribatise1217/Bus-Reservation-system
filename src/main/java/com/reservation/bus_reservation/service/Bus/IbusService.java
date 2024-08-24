package com.reservation.bus_reservation.service.Bus;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.wrapper.EditedBus;

public interface IbusService {
  public Bus addBus(Bus bus);
  public List<EditedBus> viewAllBus();
  public EditedBus viewBus(Integer id);
  //public List<EditedBus> viewBusByType(String busType);
  public Bus updateBusDetails(Bus bus, Integer id);
  public ResponseEntity<String> deleteBus(Integer id);
  public ResponseEntity<String> deleteAllBuses();
}
