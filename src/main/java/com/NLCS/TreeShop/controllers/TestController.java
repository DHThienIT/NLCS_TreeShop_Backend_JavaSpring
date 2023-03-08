package com.NLCS.TreeShop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.security.jwt.JwtUtils;
import com.NLCS.TreeShop.security.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	private TreeRepository treeRepository;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllTrees() {
		List<Tree> trees = treeRepository.findAll();
		return ResponseEntity.ok(trees);
	}

	@GetMapping("/normal_read")
	@PreAuthorize("hasRole('normal_read')")
	public String userAccess() {
		return "normal_read.";
	}

	@GetMapping("/high_read")
	@PreAuthorize("hasRole('high_read')")
	public String moderatorAccess() {
		return "high_read.";
	}

	@GetMapping("/crud_admin")
	@PreAuthorize("hasRole('crud_admin')")
	public String adminAccess() {
		return "crud_admin.";
	}
}
