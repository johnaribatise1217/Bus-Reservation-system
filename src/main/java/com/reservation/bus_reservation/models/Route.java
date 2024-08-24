package com.reservation.bus_reservation.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="route")
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer routeId;
	
	private String routeTo;
	private String routeFrom;

	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
	private List<Bus> buses;
}
