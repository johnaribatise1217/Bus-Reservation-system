package com.reservation.bus_reservation.models.wrapper;

import java.util.stream.Collectors;

import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.Reservation;
import com.reservation.bus_reservation.models.ReservationResponses.ReservationBus;
import com.reservation.bus_reservation.models.ReservationResponses.ReservationResponse;
import com.reservation.bus_reservation.models.ReservationResponses.ReservationUser;
import com.reservation.bus_reservation.models.Route;
import com.reservation.bus_reservation.models.User;

public class ModelMapper {

  public static BusWithoutRoute mapBusWithoutRoute(Bus bus){
    BusWithoutRoute busWithoutRoute = new BusWithoutRoute(
      bus.getBusId(), bus.getBusName()
      , bus.getBusType(), bus.getNumberReservation());
    return busWithoutRoute;
  }

  public static RouteWithoutBus mapRouteWithoutBus(Route route){
    RouteWithoutBus routeWithoutBus = new RouteWithoutBus(
        route.getRouteId(), route.getRouteTo(), 
        route.getRouteFrom(), 
        route.getBuses().stream().map((bus) -> ModelMapper.mapBusWithoutRoute(bus))
        .collect(Collectors.toList())
      );
    
      return routeWithoutBus;
  }

  public static RouteNoBus mapRouteNoBus(Route route){
    RouteNoBus routeNoBus = new RouteNoBus(route.getRouteId(), route.getRouteTo(), route.getRouteFrom());
    return routeNoBus;
  }

  public static EditedBus mapBusToEditedBus(Bus bus){
    EditedBus editedBus = new EditedBus(
      bus.getBusId(), bus.getBusName(), bus.getBusType(),
      bus.getSeats(), bus.getAvailableSeats(), bus.getDepartureTime(), 
      bus.getArrivalTime(), bus.getNumberReservation(), 
      ModelMapper.mapRouteNoBus(bus.getRoute()),
      bus.getReservations().stream().map((reservation) -> ModelMapper.mapReservationToReservationBus(reservation))
      .collect(Collectors.toList())
    );

    return editedBus;
  }

  public static ReservationBus mapReservationToReservationBus(Reservation reservation){
    ReservationBus reservationBus = new ReservationBus(
      reservation.getId(), reservation.getUser().getFirstname(), reservation.getUser().getLastname(),
      reservation.getReservationDate(), reservation.getReservationTime()
    );

    return reservationBus;
  }

  public static ReservationResponse mapReservationResponse(Reservation reservation){
    ReservationResponse reservationResponse = new ReservationResponse(
      reservation.getId(), reservation.getReservationTime(), reservation.getReservationDate(),
      reservation.getUserName(), reservation.getBusType(), reservation.getBusName()
    );

    return reservationResponse;
  }

  public static ReservationUser mapReservationUser(Reservation reservation){
    ReservationUser reservationUser = new ReservationUser(
      reservation.getId(), reservation.getReservationDate(), reservation.getBusName(),
      reservation.getBusType(), reservation.getSeatNo()
    );

    return reservationUser;
  }

  public static UserResponse mapUserResponse(User user){
    UserResponse userResponse = new UserResponse(
      user.getUserId(), user.getFirstname(), user.getLastname(), 
      user.getEmail(), user.getTotalReservation(),
      user.getReservations().stream().map((reservation) -> ModelMapper.mapReservationUser(reservation))
      .collect(Collectors.toList())
    );
    return userResponse;
  }
}
