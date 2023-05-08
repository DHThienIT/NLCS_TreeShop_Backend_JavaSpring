package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

	Promotion findByCode(String code);
}
