package com.NLCS.TreeShop.security.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Supplier;
import com.NLCS.TreeShop.models.Category;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.TreeRequest;
import com.NLCS.TreeShop.repository.SupplierRepository;
import com.NLCS.TreeShop.repository.CategoryRepository;
import com.NLCS.TreeShop.repository.TreeRepository;

@Service
public class TreeServiceImpl implements TreeService {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	TreeRepository treeRepository;

	@Override
	public Tree createTree(TreeRequest treeRequest) {
		Supplier supplier = supplierRepository.findById(treeRequest.getSupplier_id()).orElseThrow();
		Set<String> strCategories = treeRequest.getCategories();
		Set<Category> categories = new HashSet<>();
		
		if (strCategories == null) {
			Category categoryNone = categoryRepository.findBySymbol("NONE");
			categories.add(categoryNone);
		} else {
			strCategories.forEach(category -> {
				switch (category) {
				case "C1":
					Category category1 = categoryRepository.findBySymbol("C1");
					categories.add(category1);
					break;
					
				case "C2":
					Category category2 = categoryRepository.findBySymbol("C2");
					categories.add(category2);
					break;
					
				case "C3":
					Category category3 = categoryRepository.findBySymbol("C3");
					categories.add(category3);
					break;
					
				case "C4":
					Category category4 = categoryRepository.findBySymbol("C4");
					categories.add(category4);
					break;
					
				case "C5":
					Category category5 = categoryRepository.findBySymbol("C5");
					categories.add(category5);
					break;
					
				case "C6":
					Category category6 = categoryRepository.findBySymbol("C6");
					categories.add(category6);
					break;
					
				case "C7":
					Category category7 = categoryRepository.findBySymbol("C7");
					categories.add(category7);
					break;

				default:
					Category categoryNone = categoryRepository.findBySymbol("NONE");
					categories.add(categoryNone);
				}
			});
		}
		
		Tree tree = new Tree(treeRequest.getName(), treeRequest.getDescription(), treeRequest.getSize(),
				treeRequest.getStock(), treeRequest.getPrice(), categories, supplier);
		tree.setImageUrl(treeRequest.getImageUrl());
		return treeRepository.save(tree);
	}

	@Override
	public Optional<Tree> updateTree(Long treeId, TreeRequest treeRequest) {
		// TODO Auto-generated method stub
		Optional<Tree> tree = treeRepository.findById(treeId);
		
		if (tree.isPresent()) {
			Supplier supplier = supplierRepository.findById(treeRequest.getSupplier_id()).orElseThrow();
			tree.get().setTreeName(treeRequest.getName());
			tree.get().setDescription(treeRequest.getDescription());
			tree.get().setPrice(treeRequest.getPrice());
			tree.get().setSize(treeRequest.getSize());
			tree.get().setStock(treeRequest.getStock());
			tree.get().setImageUrl(treeRequest.getImageUrl());
			tree.get().setSupplier(supplier);
			
			Set<String> strCategories = treeRequest.getCategories();
			Set<Category> categories = new HashSet<>();
			
			strCategories.forEach(category -> {
				switch (category) {
				case "C1":
					Category category1 = categoryRepository.findBySymbol("C1");
					categories.add(category1);
					break;
					
				case "C2":
					Category category2 = categoryRepository.findBySymbol("C2");
					categories.add(category2);
					break;
					
				case "C3":
					Category category3 = categoryRepository.findBySymbol("C3");
					categories.add(category3);
					break;
					
				case "C4":
					Category category4 = categoryRepository.findBySymbol("C4");
					categories.add(category4);
					break;
					
				case "C5":
					Category category5 = categoryRepository.findBySymbol("C5");
					categories.add(category5);
					break;
					
				case "C6":
					Category category6 = categoryRepository.findBySymbol("C6");
					categories.add(category6);
					break;
					
				case "C7":
					Category category7 = categoryRepository.findBySymbol("C7");
					categories.add(category7);
					break;

				default:
					Category categoryNone = categoryRepository.findBySymbol("NONE");
					categories.add(categoryNone);
				}
			});
			tree.get().setCategories(categories);
			
			treeRepository.save(tree.get());
			return tree;
		} else {
			throw new InvalidConfigurationPropertyValueException("treeId", treeId, "Not found");
		}
	}

	@Override
	public void softDeleteTree(Long treeId) {
		// TODO Auto-generated method stub
		Tree tree = treeRepository.findById(treeId).orElseThrow(() -> new InvalidConfigurationPropertyValueException("treeId", treeId, "Not found"));
		tree.setStatus(false);
		treeRepository.save(tree);
	}
	
	@Override
	public void hardDeleteTree(Long treeId) {
		if (treeRepository.findById(treeId).get().getTreeId().equals(treeId)) {
			treeRepository.deleteById(treeId);
		} else
			throw new InvalidConfigurationPropertyValueException("treeId", treeId, "Not found");
	}

	@Override
	public Tree getTree(Long treeId) {
		return treeRepository.findById(treeId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("treeId", treeId, "Not found"));
	}

	@Override
	public Tree treeReactivationById(Long treeId) {
		Tree tree = treeRepository.findById(treeId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("treeId", treeId, "Not found"));
		tree.setStatus(true);
		return treeRepository.save(tree);
	}

	@Override
	public List<Tree> getAllTreeForShowHome() {
		return treeRepository.findByStatus(true);
	}

	@Override
	public List<Tree> getAllTreeForManage() {
		return treeRepository.findAll();
	}
}
