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

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.payload.request.AddressRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.AddressRepository;
import com.NLCS.TreeShop.repository.CityRepository;
import com.NLCS.TreeShop.repository.DistrictRepository;
import com.NLCS.TreeShop.repository.WardRepository;
import com.NLCS.TreeShop.security.services.AddressService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api/address")
public class AddressController {
	@Autowired
	AddressService addressService;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	WardRepository wardRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	CityRepository cityRepository;

	@GetMapping("/getAllAddressByUser/{userId}")
	@PreAuthorize("hasRole('ADDRESS_NORMAL_ACCESS')")
	public ResponseEntity<List<Address>> getAllAddressByUser(@PathVariable("userId") Long userId) {
		try {
			List<Address> listAddresses = addressService.getAllAddressByUser(userId);
			return new ResponseEntity<>(listAddresses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{addressId}")
	@PreAuthorize("hasRole('ADDRESS_NORMAL_ACCESS')")
	public ResponseEntity<Address> getAddressById(@PathVariable("addressId") Long addressId) {
		try {
			Address address = addressService.findAddressById(addressId);
			if (address != null)
				return new ResponseEntity<>(address, HttpStatus.OK);
			else
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/create", consumes = { "*/*" })
	@PreAuthorize("hasRole('ADDRESS_NORMAL_ACCESS')")
	public ResponseEntity<?> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
		if (addressRequest.getUser_id() == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Chưa nhập userId!"));
		}

		if (addressRepository.existsByAddress(addressRequest.getAddressName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Tên địa chỉ này đã tồn tại!"));
		}

		return new ResponseEntity<>(addressService.createAddress(addressRequest), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{addressId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('ADDRESS_NORMAL_ACCESS')")
	public ResponseEntity<?> updateAddress(@PathVariable("addressId") Long addressId,
			@RequestBody @Valid AddressRequest addressRequest) {
		return new ResponseEntity<>(addressService.updateAddress(addressId, addressRequest), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{addressId}")
	@PreAuthorize("hasRole('ADDRESS_NORMAL_ACCESS')")
	public ResponseEntity<MessageResponse> softDeleteAddressById(@PathVariable("addressId") Long addressId) {
		try {
			addressService.softDeleteAddressById(addressId);
			return ResponseEntity.ok(new MessageResponse("Dữ liệu đã bị ngắt kết nối (soft_delete)!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{addressId}")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<MessageResponse> hardDeleteAddressById(@PathVariable("addressId") Long addressId) {
		try {
			addressService.hardDeleteAddressById(addressId);
			return ResponseEntity.ok(new MessageResponse("Đã xóa dữ liệu thành công trong CSDL!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
