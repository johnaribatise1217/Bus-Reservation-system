package com.reservation.bus_reservation.service.Route;

import java.util.List;

import com.reservation.bus_reservation.models.Route;
import com.reservation.bus_reservation.models.wrapper.RouteWithoutBus;

public interface IRoute {
  Route createRoute(Route route);
  List<RouteWithoutBus> getAllRoutes();
  RouteWithoutBus getRouteById(Integer id);
  Route updateRoute(Integer id, Route route);
  String deleteRoute(Integer id);
}
