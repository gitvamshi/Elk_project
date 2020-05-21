package com.vzw.profileservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.vzw.profileservice.models.Equipment;
import com.vzw.profileservice.models.VZWCustomer;
import com.vzw.profileservice.models.VZWEquipment;
import com.vzw.profileservice.models.VZWProfile;

import reactor.core.publisher.Mono;

@Service
public class ProfileService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${customer.service.name}")
	private String customerServiceName;
	
	@Value("${customer.endpoint}")
	private String customerEndpoint;
	
	@Value("${equipment.service.name}")
	private String equipmentServiceName;
	
	@Value("${equipment.endpoint}")
	private String equipmentEndpoint;
	
	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	public Mono<VZWProfile> fetchProfile(Long customerId) {

		//VZWCustomer customer = restTemplate.getForObject("http://localhost:8080/customer/" + customerId, VZWCustomer.class);
		//VZWCustomer customer = restTemplate.getForObject("http://vzw-customer-service//api/v1/customer/" + customerId, VZWCustomer.class);
		VZWCustomer customer = null;
		Application customerApplication = eurekaClient.getApplication(customerServiceName);
		if(customerApplication != null) {
			customer = restTemplate.getForObject("http://"+customerApplication.getName()+customerEndpoint+ customerId, VZWCustomer.class);
			//List<InstanceInfo> instances = application.getInstances();
		}
		//WebClient.builder().build().method(HttpMethod.GET).

		//VZWEquipment equipment = restTemplate.getForObject("http://localhost:8081/equipment/customer/" + customerId, VZWEquipment.class);
		//VZWEquipment equipment = restTemplate.getForObject("http://vzw-equipment-service/api/v1/equipment/customer/" + customerId, VZWEquipment.class);
		Equipment[] equipment = null;
		Application equipmentApplication = eurekaClient.getApplication(equipmentServiceName);
		if(equipmentApplication != null) {
			equipment = restTemplate.getForObject("http://"+equipmentApplication.getName()+equipmentEndpoint + customerId, Equipment[].class);
		}
		
		VZWProfile vzwProfile = new VZWProfile();
		vzwProfile.setCustomer_id(customerId);
		if(customer != null) {
			vzwProfile.setFirstName(customer.getFirstName());
			vzwProfile.setLastName(customer.getLastName());
			vzwProfile.setAddress(customer.getAddress());
		}
		if(equipment != null) {
			vzwProfile.setEquipment(Arrays.asList(equipment));
		}
		

		return Mono.just(vzwProfile);
	}

}
