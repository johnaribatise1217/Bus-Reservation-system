package com.reservation.bus_reservation.models.wrapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteWithoutBus {
  private Integer id;
  private String routeTo;
  private String routeFrom;

  List<BusWithoutRoute> buses;
}
