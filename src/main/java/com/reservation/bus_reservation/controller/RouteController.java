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

import com.reservation.bus_reservation.models.Route;
import com.reservation.bus_reservation.models.wrapper.RouteWithoutBus;
import com.reservation.bus_reservation.service.Route.RouteService;

@RestController
@RequestMapping("/api/route")
public class RouteController {
  @Autowired
  private RouteService routeService;

  @PostMapping("create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> createRoute(@RequestBody Route route){
    try {
      routeService.createRoute(route);
      return ResponseEntity.status(HttpStatus.CREATED).body("Route created Successfully");
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("get-all")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<RouteWithoutBus>> getAllRoutes(){
    try {
      List<RouteWithoutBus> allRoutes = routeService.getAllRoutes();
      return ResponseEntity.status(HttpStatus.OK).body(allRoutes);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("get/{id}")
  @PreAuthorize("hasAuthority('PASSENGER', 'ADMIN', 'BUS_DRIVER')")
  public ResponseEntity<RouteWithoutBus> getRouteById(@PathVariable("id") Integer id ){
    return ResponseEntity.status(HttpStatus.FOUND).body(
      routeService.getRouteById(id)
    );
  }

  @PutMapping("/update-route/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> updateRoute(@PathVariable("id") Integer id, @RequestBody Route route){
    try {
      routeService.updateRoute(id, route);
      return ResponseEntity.status(HttpStatus.OK).body("Route updated Successfully.");
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("delete/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteRoute(@PathVariable("id") Integer id){
    try {
      routeService.deleteRoute(id);
      return ResponseEntity.status(HttpStatus.OK).body("Route deleted Successfully.");
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
