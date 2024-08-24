package com.reservation.bus_reservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.BusType;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer>{
	List<Bus> findByBusType(String busType);
	Optional<Bus> findBusByBusNameAndBusType(String busName, BusType busType);
}
