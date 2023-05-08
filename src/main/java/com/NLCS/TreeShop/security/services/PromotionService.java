package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Promotion;

@Component
public interface PromotionService {
	List<Promotion> getAll();

	String checkPromotionCode(String code);
}
