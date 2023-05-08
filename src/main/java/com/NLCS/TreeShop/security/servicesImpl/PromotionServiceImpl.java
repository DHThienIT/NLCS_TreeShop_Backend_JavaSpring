package com.NLCS.TreeShop.security.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Promotion;
import com.NLCS.TreeShop.repository.PromotionRepository;
import com.NLCS.TreeShop.security.services.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	PromotionRepository promotionRepository;

	@Override
	public List<Promotion> getAll() {
		return promotionRepository.findAll();
	}

	@Override
	public String checkPromotionCode(String code) {
		String promotionCodeIsExits = "";
		Promotion promotion = promotionRepository.findByCode(code);
		if (promotion != null)
			promotionCodeIsExits = promotion.getPromotionalPrice();
		else
			promotionCodeIsExits = "0";
		return promotionCodeIsExits;
	}

}
