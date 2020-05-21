package com.vzw.equipmentservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzw.equipmentservice.domain.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>{
	
	List<Equipment> findByCustomerId(Long customerId);
	Optional<Equipment> findByCustomerIdAndName(Long customerId, String name);

}
