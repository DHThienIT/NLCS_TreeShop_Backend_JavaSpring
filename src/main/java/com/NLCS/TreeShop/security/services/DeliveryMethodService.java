package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.DeliveryMethod;

@Component
public interface DeliveryMethodService {
	List<DeliveryMethod> getAll();
}
