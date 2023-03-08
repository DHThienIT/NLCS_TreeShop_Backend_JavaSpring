package com.NLCS.TreeShop.controllers;

import java.util.List;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.security.services.TreeSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController {
	@Autowired
	TreeSearchService productSearchService;
	
	/*search product case sensitive*/
	@GetMapping("/product/{text}")
	@PreAuthorize("hasRole('SEARCH_NORMAL_ACCESS')")
	public ResponseEntity<List<Tree>> searchProduct(@PathVariable("text") String text)
	{
		try {
			List<Tree> result = productSearchService.searchTreeByName(text);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
