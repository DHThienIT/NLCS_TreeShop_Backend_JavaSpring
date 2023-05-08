package com.NLCS.TreeShop.security.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Category;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.CategoryRequest;
import com.NLCS.TreeShop.repository.CategoryRepository;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.security.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	TreeRepository treeRepository;;

	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Tree> filterTreeByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow();

		List<Tree> trees = treeRepository.findAll();
		List<Tree> trees2 = new ArrayList<>();

		for (Tree tree : trees) {
			Set<Category> categories = tree.getCategories();
			for (Category category0 : categories) {
				if (category0.equals(category)) {
					trees2.add(tree);
				}
			}
		}
		return trees2;
	}

//	@Override
//	public Page<Tree> filterTreeByCategory(Long categoryId, Optional<Integer> page, Optional<String> sortBy) {
//		Category category = categoryRepository.findById(categoryId).orElseThrow();
//
//		List<Tree> trees = treeRepository.findAll();
//		List<Tree> trees2 = new ArrayList<>();
//
//		for (Tree tree : trees) {
//			Set<Category> categories = tree.getCategories();
//			for (Category category0 : categories) {
//				if (category0.equals(category)) {
//					trees2.add(tree);
//				}
//			}
//		}
//		System.out.println("length " + trees2.size());
//		Page<Tree> treePage = new PageImpl<>(trees2,
//				PageRequest.of(page.orElse(0), 6, Sort.Direction.DESC, sortBy.orElse("treeId")), 2);
//		System.out.println("treePage " + treePage);
//		return treePage;
//	}

	@Override
	public Category createCategory(CategoryRequest categoryRequest) {
		Category category = new Category(categoryRequest.getSymbol(), categoryRequest.getDetails());
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Long categoryId, CategoryRequest categoryRequest) {
		Category category = categoryRepository.findById(categoryId).orElseThrow();
		if (category != null) {
			category.setDetail(categoryRequest.getDetails());
			categoryRepository.save(category);
			return category;
		} else {
			throw new InvalidConfigurationPropertyValueException("categoryId", categoryId, "Not Found");
		}
	}

	@Override
	public void hardDeleteCategory(Long categoryId) {
		if (categoryRepository.findById(categoryId).get().getCategoryId().equals(categoryId)) {
			categoryRepository.deleteById(categoryId);
		} else
			throw new InvalidConfigurationPropertyValueException("categoryId", categoryId, "Not Found");
	}
}
