package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Category;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.CategoryRequest;

@Component
public interface CategoryService {

	List<Category> getAll();

	List<Tree> filterTreeByCategory(Long categoryId);

	Category createCategory(CategoryRequest categoryRequest);
	
	Category updateCategory(Long categoryId, CategoryRequest categoryRequest);

	void hardDeleteCategory(Long categoryId);
}
