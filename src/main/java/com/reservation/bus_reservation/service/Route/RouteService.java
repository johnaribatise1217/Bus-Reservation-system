package com.reservation.bus_reservation.service.Route;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.bus_reservation.Exception.ResourceNotFound;
import com.reservation.bus_reservation.models.Route;
import com.reservation.bus_reservation.models.wrapper.ModelMapper;
import com.reservation.bus_reservation.models.wrapper.RouteWithoutBus;
import com.reservation.bus_reservation.repository.RouteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteService implements IRoute {
  @Autowired
  private final RouteRepository routeRepository;

  @Override
  public Route createRoute(Route route) {
    return routeRepository.save(route);
  }

  @Override
  public List<RouteWithoutBus> getAllRoutes() {
    List<Route> allExistingRoutes = routeRepository.findAll();
    
    return allExistingRoutes.stream().map((existingRoutes) -> ModelMapper.mapRouteWithoutBus(existingRoutes))
    .collect(Collectors.toList());
  }

  @Override
  public RouteWithoutBus getRouteById(Integer id) {
    Route existingRoute = routeRepository.findById(id).orElseThrow(
      () ->  new ResourceNotFound("Route", "existingRoute", id));
    return ModelMapper.mapRouteWithoutBus(existingRoute);
  }

  @Override
  public Route updateRoute(Integer id, Route route) {
    //find the route with the pathvariable id
    Route existingRoute = routeRepository.findById(id).orElseThrow(
      () -> new ResourceNotFound("Route", "existing route", id));
    
    existingRoute.setRouteFrom(route.getRouteFrom());
    existingRoute.setRouteTo(route.getRouteTo());

    routeRepository.save(existingRoute);
    return existingRoute;
  }

  @Override
  public String deleteRoute(Integer id) {
    Route routeToDelete = routeRepository.findById(id).orElseThrow(
      () -> new ResourceNotFound("Route", "route to delete", id));
    
    routeRepository.delete(routeToDelete);
    return "Route deleted Successfully";
  }

}
