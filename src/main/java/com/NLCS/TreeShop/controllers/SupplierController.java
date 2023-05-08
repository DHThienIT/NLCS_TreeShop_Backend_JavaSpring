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

import com.NLCS.TreeShop.models.Supplier;
import com.NLCS.TreeShop.payload.request.SupplierRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.SupplierRepository;
import com.NLCS.TreeShop.security.services.SupplierService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api/supplier")
public class SupplierController {
	@Autowired
	SupplierService supplierService;
	@Autowired
	SupplierRepository supplierRepository;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		List<Supplier> suppliers = supplierService.getAll();
		
		return ResponseEntity.ok(suppliers);
	}

	@GetMapping("/{supplierId}")
	public ResponseEntity<?> getSupplierById(@PathVariable("supplierId") Long supplierId) {
		Supplier supplier = supplierService.findSupplierById(supplierId);
		return ResponseEntity.ok(supplier);
	}

	@PostMapping(value = "/create", consumes = { "*/*" })
	@PreAuthorize("hasRole('SUPPLIER_HARD_ACCESS')")
	public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
		List<Supplier> suppliers = supplierService.getAll();
		for (Supplier supplier : suppliers) {
			if (supplier.getSupplierName().equals(supplierRequest.getSupplierName()))
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Lỗi: Tên của nhà cung cấp này đã tồn tại!"));
		}
		return new ResponseEntity<>(supplierService.createSupplier(supplierRequest), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{supplierId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('SUPPLIER_HARD_ACCESS')")
	public ResponseEntity<?> updateSupplier(@PathVariable("supplierId") Long supplierId,
			@RequestBody @Valid SupplierRequest supplierRequest) {
		List<Supplier> suppliers = supplierService.getAll();
		for (Supplier supplier : suppliers) {
			if (supplier.getSupplierName().equals(supplierRequest.getSupplierName()))
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Lỗi: Không thể cập nhật vì tên của nhà cung cấp này đã tồn tại!"));
		}

		return new ResponseEntity<>(supplierService.updateSupplier(supplierId, supplierRequest), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{supplierId}")
	@PreAuthorize("hasRole('SUPPLIER_HARD_ACCESS')")
	public ResponseEntity<MessageResponse> softDeleteSupplierById(@PathVariable("supplierId") Long supplierId) {
		try {
			supplierService.softDeleteSupplierById(supplierId);
			return ResponseEntity.ok(new MessageResponse("Dữ liệu đã bị ngắt kết nối (soft_delete)!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/{supplierId}")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<MessageResponse> hardDeleteSupplier(@PathVariable("supplierId") Long supplierId) {
		try {
			supplierService.hardDeleteSupplier(supplierId);
			return ResponseEntity.ok(new MessageResponse("Đã xóa dữ liệu thành công trong CSDL!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/reactivation/{supplierId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<?> supplierReactivationById(@PathVariable("supplierId") Long supplierId) {
		return new ResponseEntity<>(supplierService.supplierReactivationById(supplierId), HttpStatus.CREATED);
	}
}
