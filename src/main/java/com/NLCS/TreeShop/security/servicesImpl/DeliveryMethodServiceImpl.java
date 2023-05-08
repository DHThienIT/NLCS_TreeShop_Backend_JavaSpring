package com.NLCS.TreeShop.security.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.DeliveryMethod;
import com.NLCS.TreeShop.repository.DeliveryMethodRepository;
import com.NLCS.TreeShop.security.services.DeliveryMethodService;

@Service
public class DeliveryMethodServiceImpl implements DeliveryMethodService {
	@Autowired
	DeliveryMethodRepository deliveryMethodRepository;

	@Override
	public List<DeliveryMethod> getAll() {
		return deliveryMethodRepository.findAll();
	}
}
