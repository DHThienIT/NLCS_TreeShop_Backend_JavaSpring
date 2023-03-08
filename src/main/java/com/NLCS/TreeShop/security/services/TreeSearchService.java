package com.NLCS.TreeShop.security.services;

import java.util.List;


import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Tree;

@Component
public interface TreeSearchService {
	List<Tree> searchTreeByName(String text); 
}
