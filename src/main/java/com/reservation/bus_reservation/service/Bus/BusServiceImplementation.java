package com.reservation.bus_reservation.service.Bus;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reservation.bus_reservation.Exception.ResourceNotFound;
import com.reservation.bus_reservation.models.Bus;
import com.reservation.bus_reservation.models.Route;
import com.reservation.bus_reservation.models.wrapper.EditedBus;
import com.reservation.bus_reservation.models.wrapper.ModelMapper;
import com.reservation.bus_reservation.repository.BusRepository;
import com.reservation.bus_reservation.repository.RouteRepository;

@Service
public class BusServiceImplementation implements IbusService {
  @Autowired
  private BusRepository busRepository;
  @Autowired
  private RouteRepository routeRepository;

  @Override
  public Bus addBus(Bus bus) {
		Route route = routeRepository.findByRouteFromAndRouteTo(bus.getRoute().getRouteFrom(), bus.getRoute().getRouteTo())
		.orElseGet(() -> {
			Route newRoute = new Route();
			newRoute.setRouteFrom(bus.getRoute().getRouteFrom());
			newRoute.setRouteTo(bus.getRoute().getRouteTo());
			return routeRepository.save(newRoute);
		});
	
		bus.setRoute(route);
    return busRepository.save(bus);
  }

  @Override
  public List<EditedBus> viewAllBus(){
	 List<Bus> existingBuses = busRepository.findAll();

	 return existingBuses.stream().map((bus) -> ModelMapper.mapBusToEditedBus(bus))
	 .collect(Collectors.toList());
  }
  
  @Override
  public EditedBus viewBus(Integer id) {
  	//find the Bus with that particular id
	Bus busOptional = busRepository.findById(id).orElseThrow(
		() -> new ResourceNotFound("Bus", "Id", id));
	
	return ModelMapper.mapBusToEditedBus(busOptional);
  }
  
  @Override
  public Bus updateBusDetails(Bus bus, Integer id) {
	//find the bus with the id
  	Bus existingBus = busRepository.findById(id).
  			orElseThrow(() -> new ResourceNotFound("Bus", "bus", id));
  	
  	//set the existingBus attributes to the bus passed as the parameter
  	existingBus.setAvailableSeats(bus.getAvailableSeats());
  	existingBus.setBusName(bus.getBusName());
  	existingBus.setArrivalTime(bus.getArrivalTime());
  	existingBus.setBusType(bus.getBusType());
  	existingBus.setDepartureTime(bus.getDepartureTime());
  	existingBus.setRoute(bus.getRoute());
  	existingBus.setSeats(bus.getSeats());
  
  	busRepository.save(existingBus);
  	
  	return existingBus;
  }
  
  @Override
  public ResponseEntity<String> deleteBus(Integer id) {
	 Bus existingBus = busRepository.findById(id)
			 .orElseThrow(() -> new ResourceNotFound("Bus", "bus", id));
	 
	 busRepository.delete(existingBus);
	 
	 return new ResponseEntity<>("Bus Deleted successfully", HttpStatus.OK);
  }

	@Override 
	public ResponseEntity<String> deleteAllBuses(){
		List<Bus> allBuses = busRepository.findAll();
		busRepository.deleteAll(allBuses);

		return new ResponseEntity<>("All Buses deleted successfully", HttpStatus.OK);
	}
}
