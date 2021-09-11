package com.terminus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.terminus.entity.PhoneNumberEntity;
import com.terminus.repository.PhoneNumberRepository;

/**
 * Controller for the Rest API's
 */
@RestController
@JsonInclude(Include.NON_EMPTY)
public class PhoneNumberController {

	@Autowired
	PhoneNumberRepository repo;

	@GetMapping("/getAllPhoneNumbers")
	@Cacheable(value = "phoneDetails", key = "#root.method")
	public List<PhoneNumberEntity> getAllPhoneNumbers() {
		System.out.println("in get all data");
		return repo.findAll();
	}

	@GetMapping("/getPhoneNumbers/{user}")
	public List<PhoneNumberEntity> getAllPhoneNumbers(@PathVariable("user") int user) {
		return repo.findByCustomerID(user);
	}

	@PatchMapping("/activatePhoneNumbers")
	public ResponseEntity<PhoneNumberEntity> updatePhoneNumber(@RequestBody PhoneNumberEntity pn) {
		if (repo.updateStatus(pn.getStatus(), pn.getCustomerID(), pn.getPhoneNumber()) > 0)
			return new ResponseEntity<PhoneNumberEntity>(pn, HttpStatus.OK);
		else
			return new ResponseEntity<PhoneNumberEntity>(HttpStatus.NOT_FOUND);
	}

}
