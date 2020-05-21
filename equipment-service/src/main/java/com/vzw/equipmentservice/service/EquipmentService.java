package com.vzw.equipmentservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzw.equipmentservice.domain.Equipment;
import com.vzw.equipmentservice.repository.EquipmentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EquipmentService {

	@Autowired
	private EquipmentRepository repository;

	// to get all the equipments
	public Flux<Equipment> fetchAllEquipments() {
		List<Equipment> findAll = repository.findAll();
		return Flux.fromIterable(findAll);
	}

	// to get equipment based on eqipmentId
	public Mono<Equipment> fetchEquipmentById(Long equipmentId) {
		Optional<Equipment> findById = repository.findById(equipmentId);
		return Mono.justOrEmpty(findById);
	}
	
	// to get equipment based on customerId
	public Mono<List<Equipment>> fetchEquipmentByCustomerId(Long customerId) {
		List<Equipment> equipments = repository.findByCustomerId(customerId);
		return Mono.just(equipments);
	}

	// to add equipment into DB
	public Mono<String> addEquipment(Equipment equipment) {
		Optional<Equipment> custEquipment = repository.findByCustomerIdAndName(equipment.getCustomerId(), equipment.getName());
		if(!custEquipment.isPresent()) {
			repository.save(equipment);
		}
		return Mono.just("Equipment saved successfully with id="+equipment.getId());
	}
	
	// to update equipment based on id
	public Mono<String> updateEquipment(Equipment equipment) {
		return Mono.just("Equipment updated successfully for id="+equipment.getId());
	}

//	public Mono<String> mapEquipment(CustomerEquipment equipment) {
//		Optional<CustomerEquipment> custEquipment = equipmentRepository.findByCustomerIdAndEquipmentId(equipment.getCustomerId(), equipment.getEquipmentId());
//		if(!custEquipment.isPresent()) {
//			equipmentRepository.save(equipment);
//		}
//
//		return Mono.just("Successfully mapped!");
//	}
//
//	public Mono<VZWEquipment> fetchEquipmentByCustomerId(Long customerId) {
//		List<CustomerEquipment> findByCustomerId = equipmentRepository.findByCustomerId(customerId);
//		List<Long> equipmentIds = findByCustomerId.stream().map(m->m.getEquipmentId()).collect(Collectors.toList());
//		List<Equipment> equipments = repository.findAllById(equipmentIds);
//		VZWEquipment vzwEquipment = new VZWEquipment();
//		vzwEquipment.setCustomer_id(customerId);
//		vzwEquipment.setEquipment(equipments);
//		return Mono.just(vzwEquipment);
//	}

}
