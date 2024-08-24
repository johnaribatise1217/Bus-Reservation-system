package com.reservation.bus_reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.bus_reservation.models.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer>{
	Optional<Route> findByRouteFromAndRouteTo(String routeFrom, String routeTo);
}
