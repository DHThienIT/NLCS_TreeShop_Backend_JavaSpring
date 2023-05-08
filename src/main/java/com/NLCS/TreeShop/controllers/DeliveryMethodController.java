package com.NLCS.TreeShop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NLCS.TreeShop.models.DeliveryMethod;
import com.NLCS.TreeShop.security.services.DeliveryMethodService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/deliveryMethod")
public class DeliveryMethodController {
	@Autowired
	DeliveryMethodService deliveryMethodService;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		List<DeliveryMethod> deliveryMethods = deliveryMethodService.getAll();
		return ResponseEntity.ok(deliveryMethods);
	}
}
