package com.NLCS.TreeShop.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NLCS.TreeShop.models.Category;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.CategoryRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.CategoryRepository;
import com.NLCS.TreeShop.security.services.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		List<Category> categories = categoryService.getAll();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/filterTree/{categoryId}")
	public ResponseEntity<?> filterTree(@PathVariable("categoryId") Long categoryId) {
		List<Tree> trees = categoryService.filterTreeByCategory(categoryId);
		return ResponseEntity.ok(trees);
	}

	@PostMapping(value = "/create", consumes = { "*/*" })
	@PreAuthorize("hasRole('CATEGORY_NORMAL_ACCESS')")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
		if (categoryRequest.getSymbol() == null) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Lỗi: Chưa nhập vào ký hiệu của thể loại cây này!"));
		}

		if (categoryRepository.existsBySymbol(categoryRequest.getSymbol())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Lỗi: Ký hiệu của thể loại cây này đã tồn tại!"));
		}

		if (categoryRepository.existsByDetail(categoryRequest.getDetails())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Lỗi: Chi tiết tên của thể loại cây này đã tồn tại!"));
		}

		return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{categoryId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('CATEGORY_NORMAL_ACCESS')")
	public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Long categoryId,
			@RequestBody @Valid CategoryRequest categoryRequest) {
		if (categoryRequest.getSymbol() != null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Chỉ được phép cập nhật 'details'"));
		}

		return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryRequest), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/{categoryId}")
	@PreAuthorize("hasRole('CATEGORY_NORMAL_ACCESS')")
	public ResponseEntity<MessageResponse> hardDeleteCategory(@PathVariable("categoryId") Long categoryId) {
		try {
			categoryService.hardDeleteCategory(categoryId);
			return ResponseEntity.ok(new MessageResponse("Đã xóa dữ liệu thành công trong CSDL!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
