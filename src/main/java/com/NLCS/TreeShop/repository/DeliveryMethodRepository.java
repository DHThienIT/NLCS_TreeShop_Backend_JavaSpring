package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.DeliveryMethod;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
	
}
