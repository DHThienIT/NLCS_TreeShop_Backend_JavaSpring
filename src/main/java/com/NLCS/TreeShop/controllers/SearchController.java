package com.NLCS.TreeShop.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.ColorRequest;
import com.NLCS.TreeShop.payload.request.MinMaxRequest;
import com.NLCS.TreeShop.security.services.TreeSearchService;
import com.NLCS.TreeShop.security.services.TreeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController {
	@Autowired
	TreeSearchService treeSearchService;
	@Autowired
	TreeService treeService;

	@PostMapping("/{filterText}")
	public ResponseEntity<?> searchTrees(@PathVariable("filterText") String filterText) {
		List<Tree> trees;
		System.out.println("xxx: " + filterText);
		if (filterText.equals("all")) {
			trees = treeService.getAllTreeForShowHome();
			System.out.println("1: " + trees);
			return ResponseEntity.ok(trees);
		} else {
			trees = treeSearchService.searchTreeByName(filterText);
			System.out.println("2: " + trees);
			return ResponseEntity.ok(trees);
		}
	}

	@PostMapping("/filterByMaxMinPrice")
	public ResponseEntity<?> searchTreesByMaxMinPrice(@Valid @RequestBody MinMaxRequest minMaxRequest) {
		List<Tree> trees = new ArrayList<>();
		if (minMaxRequest.getMax() == 0) {
			trees = treeSearchService.searchTreesByMinPrice(minMaxRequest.getMin());
		} else
			trees = treeSearchService.searchTreesByMaxMinPrice(minMaxRequest.getMin(), minMaxRequest.getMax());
		return ResponseEntity.ok(trees);
	}

	@PostMapping("/filterByTreeColor")
	public ResponseEntity<?> searchTreesByTreeColor(@Valid @RequestBody ColorRequest colorRequest) {
		List<Tree> trees = new ArrayList<>();
		trees = treeSearchService.searchTreeByColor(colorRequest.getListColor());
		return ResponseEntity.ok(trees);
	}
}
