package com.NLCS.TreeShop.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.models.Invoice;
import com.NLCS.TreeShop.payload.request.InvoiceRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.AddressRepository;
import com.NLCS.TreeShop.repository.InvoiceRepository;
import com.NLCS.TreeShop.security.services.InvoiceService;

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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	AddressRepository addressRepository;

	@PostMapping(value = "/create", consumes = { "*/*" })
	@PreAuthorize("hasRole('INVOICE_NORMAL_ACCESS')")
	public ResponseEntity<Invoice> creatInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) {
		return new ResponseEntity<>(invoiceService.creatInvoice(invoiceRequest), HttpStatus.CREATED);
	}

//	Lấy ra toàn bộ hóa đơn để Admin xem
	@GetMapping("/getAllInvoices")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<List<Invoice>> getAllInvoices() {
		try {
			List<Invoice> lstInvoices = invoiceService.getAllInvoices();
			return new ResponseEntity<>(lstInvoices, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	Lấy ra toàn bộ hóa đơn của người dùng A đã thanh toán thành công
	@GetMapping("/getAllInvoicesPaySuccessByUser/{userId}")
	@PreAuthorize("hasRole('INVOICE_NORMAL_ACCESS')")
	public ResponseEntity<List<Invoice>> getInvoicesByUser(@PathVariable("userId") Long userId) {
		try {
			List<Invoice> lstInvoices = invoiceService.getAllInvoicesPaySuccessByUser(userId);
			return new ResponseEntity<>(lstInvoices, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	Lấy ra chỉ 1 hóa đơn của người dùng A (khi người dùng bấm từ giỏ hàng vào thanh toán hoặc khi bấm vào xem chi tiết 1 hóa đơn đã thành công nào đó)
	@GetMapping("/getInvoice/{invoiceId}")
	@PreAuthorize("hasRole('INVOICE_NORMAL_ACCESS')")
	public ResponseEntity<?> getInvoice(@PathVariable("invoiceId") Long invoiceId) {
		try {
			Invoice invoice = invoiceService.getInvoice(invoiceId);
			return ResponseEntity.ok(invoice);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	Chỉ MODERATOR hoặc ADMIN mới có quyền xóa
	@DeleteMapping("/{invoiceId}")
	@PreAuthorize("hasRole('INVOICE_NORMAL_ACCESS')")
	public ResponseEntity<?> softDeleteInvoice(@PathVariable("invoiceId") Long invoiceId) {
		try {
			invoiceService.softDeleteInvoiceById(invoiceId);
			return ResponseEntity.ok(new MessageResponse("Dữ liệu đã bị ngắt kết nối (soft_delete)!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	Chỉ MODERATOR hoặc ADMIN mới có quyền xóa
	@DeleteMapping("/delete/{invoiceId}")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<?> hardDeleteInvoice(@PathVariable("invoiceId") Long invoiceId) {
		try {
			invoiceService.hardDeleteInvoiceById(invoiceId);
			return ResponseEntity.ok(new MessageResponse("Đã xóa dữ liệu thành công trong CSDL!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PutMapping(value = "/updateInvoice/{invoiceId}", consumes = { "*/*" })
//	@PreAuthorize("hasRole('INVOICE_NORMAL_ACCESS')")
//	public ResponseEntity<?> updateProductsAndAddressInInvoice(
//			@PathVariable("invoiceId") Long invoiceId, @Valid @RequestBody InvoiceRequest invoiceRequest) {
//		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
//		List<Address> addresses = addressRepository.findByUser_UserIdAndStatus(invoice.getUser().getUserId(), true);
//		
//		if(addresses.isEmpty()) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Tài khoản hiện đang không có địa chỉ nào, xin vui lòng thêm địa chỉ giao hàng mới!"));
//		}
//		
//		Address address = addressRepository.findById(invoiceRequest.getAddress_id()).orElseThrow();
//
//		boolean check = false;
//		for (Address address0 : addresses) {
//			if (address.equals(address0)) {
//				check = true;
//			}
//		}
//
//		if (!invoice.getAddress().equals(address)) {
//			if (check) {
//				return new ResponseEntity<>(invoiceService.updateAddressInInvoice(invoice, address), HttpStatus.OK);
//			}
//		}
//		if (invoice.isWasPay() == false) {
//		return new ResponseEntity<>(invoiceService.updateProductsInInvoice(invoice, invoiceRequest.getUser_id()),
//				HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PutMapping(value = "/reactivation/{invoiceId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('INVOICE_NORMAL_ACCESS')")
	public ResponseEntity<?> treeReactivationById(@PathVariable("invoiceId") Long invoiceId) {
		return new ResponseEntity<>(invoiceService.invoiceReactivationById(invoiceId), HttpStatus.CREATED);
	}
}
