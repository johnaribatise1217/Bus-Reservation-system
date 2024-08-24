package com.reservation.bus_reservation.models.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteNoBus {
  private Integer id;
  private String routeTo;
  private String routeFrom;
}
