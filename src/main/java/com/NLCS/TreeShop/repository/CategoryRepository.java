package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findBySymbol(String string);

	boolean existsBySymbol(String symbol);

	boolean existsByDetail(String details);
	
}
