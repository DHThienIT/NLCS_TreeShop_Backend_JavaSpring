package com.NLCS.TreeShop.controllers;

import java.util.List;
import java.util.Optional;

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

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.TreeRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.security.services.TreeService;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tree")
public class TreeController {
	@Autowired
	TreeService treeService;
	@Autowired
	TreeRepository treeRepository;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllTreeForShowHome() {
		List<Tree> trees = treeService.getAllTreeForShowHome();
		return ResponseEntity.ok(trees);
	}
	
	@GetMapping("/getAllTrees")
	@PreAuthorize("hasRole('TREE_HARD_ACCESS')")
	public ResponseEntity<?> getAllTreeForManage() {
		List<Tree> trees = treeService.getAllTreeForManage();
		return ResponseEntity.ok(trees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tree> getTreeById(@PathVariable("id") Long id) {
		Tree tree = treeService.getTree(id);
		return ResponseEntity.ok(tree);
	}

	@PostMapping(value = "/create", consumes = { "*/*" })
	@PreAuthorize("hasRole('TREE_HARD_ACCESS')")
	public ResponseEntity<?> createTree(@Valid @RequestBody TreeRequest treeRequest) {
		if (treeRepository.existsByTreeName(treeRequest.getName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Tên cây này đã tồn tại!"));
		}

		return new ResponseEntity<>(treeService.createTree(treeRequest), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{treeId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('TREE_HARD_ACCESS')")
	public ResponseEntity<Optional<Tree>> updateTree(@PathVariable("treeId") Long treeId,
			@RequestBody @Valid TreeRequest treeRequest) {
		return new ResponseEntity<>(treeService.updateTree(treeId, treeRequest), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{treeId}")
	@PreAuthorize("hasRole('TREE_HARD_ACCESS')")
	public ResponseEntity<MessageResponse> softDeleteTree(@PathVariable("treeId") Long treeId) {
		try {
			treeService.softDeleteTree(treeId);
			return ResponseEntity.ok(new MessageResponse("Dữ liệu đã bị ngắt kết nối (soft_delete)!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/{treeId}")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<MessageResponse> hardDeleteTree(@PathVariable("treeId") Long treeId) {
		try {
			treeService.hardDeleteTree(treeId);
			return ResponseEntity.ok(new MessageResponse("Đã xóa dữ liệu thành công trong CSDL!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/reactivation/{treeId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('TREE_HARD_ACCESS')")
	public ResponseEntity<?> treeReactivationById(@PathVariable("treeId") Long treeId) {
		return new ResponseEntity<>(treeService.treeReactivationById(treeId), HttpStatus.CREATED);
	}
}
