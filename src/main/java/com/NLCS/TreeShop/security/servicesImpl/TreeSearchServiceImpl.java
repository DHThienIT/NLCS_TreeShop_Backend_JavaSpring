package com.NLCS.TreeShop.security.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.EColor;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.security.services.TreeSearchService;

@Service
public class TreeSearchServiceImpl implements TreeSearchService {
	@Autowired
	TreeRepository treeRepository;

	@Override
	public List<Tree> searchTreeByName(String filterText) {
		return treeRepository.findByNameLike(filterText);
	}

	@Override
	public List<Tree> searchTreesByMinPrice(int min) {
		List<Tree> trees = treeRepository.findByMinPrice(min);
		return trees;
	}

	@Override
	public List<Tree> searchTreesByMaxMinPrice(int min, int max) {
		List<Tree> trees = treeRepository.findByMaxMinPrice(min, max);
		return trees;
	}

	@Override
	public List<Tree> searchTreeByColor(List<EColor> listColor) {
		boolean checkOpen = false;
		List<String> lists = new ArrayList<>();
		for (EColor obj : listColor) {
			if(obj.getName() != "NONE") checkOpen = true;
			lists.add(obj.getName());
		}
		List<Tree> trees = new ArrayList<>();
		if(checkOpen) {
			String[] colors = lists.toArray(new String[0]);
			trees = treeRepository.findByColor(colors);
		} else {
			trees = null;
		}
		
		return trees;
	}
}
