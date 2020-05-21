package com.vzw.equipmentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vzw.equipmentservice.domain.Equipment;
import com.vzw.equipmentservice.service.EquipmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {
	
	@Autowired
	private EquipmentService equipmentService;
	
	@GetMapping("/getAllEquiments")
	public Flux<Equipment> getEquipments() {
		return equipmentService.fetchAllEquipments();
	}
	
	@GetMapping(path="/{id}",produces = "application/json")
	public Mono<Equipment> getEquipmentBasedOnId(@PathVariable(name = "id") Long equipmentId) {
		
		return equipmentService.fetchEquipmentById(equipmentId);
	}
	
	@GetMapping(path="/customer/{customer_id}",produces = "application/json")
	public Mono<List<Equipment>> getEqipmentBasedOnCustomerId(@PathVariable(name = "customer_id") Long equipmentId) {
		
		return equipmentService.fetchEquipmentByCustomerId(equipmentId);
	}
	
	@PostMapping("/addEquipment")
	public Mono<String> addCustomer(@RequestBody Equipment equipment) {
		return equipmentService.addEquipment(equipment);
	}
	
//	@PostMapping("/map")
//	public Mono<String> addCustomer(@RequestBody CustomerEquipment equipment) {
//		return equipmentService.mapEquipment(equipment);
//	}
}
