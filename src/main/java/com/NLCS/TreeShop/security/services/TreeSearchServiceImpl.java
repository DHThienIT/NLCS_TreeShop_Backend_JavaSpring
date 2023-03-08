package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.repository.TreeRepository;

@Service
public class TreeSearchServiceImpl implements TreeSearchService{
	@Autowired
	TreeRepository productRepo;
	
	@Override
	public List<Tree> searchTreeByName(String text) {
		// TODO Auto-generated method stub
		
		return productRepo.findByNameLike(text);
	}
}
