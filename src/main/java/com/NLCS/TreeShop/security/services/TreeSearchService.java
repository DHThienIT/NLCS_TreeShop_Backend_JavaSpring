package com.NLCS.TreeShop.security.services;

import java.util.List;


import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.EColor;
import com.NLCS.TreeShop.models.Tree;

@Component
public interface TreeSearchService {
	List<Tree> searchTreeByName(String filterText);
	
	List<Tree> searchTreesByMinPrice(int min); 

	List<Tree> searchTreesByMaxMinPrice(int min, int max);

	List<Tree> searchTreeByColor(List<EColor> listColor);
}
