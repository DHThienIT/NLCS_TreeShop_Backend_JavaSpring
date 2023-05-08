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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	private TreeRepository treeRepository;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllTrees() throws Exception {
		List<Tree> trees = treeRepository.findAll();
		return ResponseEntity.ok(trees);
//		EmailSender.sendEmail("thienb1910452@student.ctu.edu.vn", "Java Example Test", "Hello Admin");
//		return null;
	}

	@GetMapping("/normal_read")
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public String userAccess() {
		return "normal_read.";
	}

	@GetMapping("/high_read")
	@PreAuthorize("hasRole('high_read')")
	public String moderatorAccess() {
		return "high_read.";
	}

	@GetMapping("/crud_admin")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public String adminAccess() {
		return "crud_admin.";
	}
}
