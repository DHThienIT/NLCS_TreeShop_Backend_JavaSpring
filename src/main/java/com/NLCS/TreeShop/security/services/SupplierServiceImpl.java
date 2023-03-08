package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Supplier;
import com.NLCS.TreeShop.payload.request.SupplierRequest;
import com.NLCS.TreeShop.repository.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	SupplierRepository supplierRepository;

	@Override
	public List<Supplier> getAll() {
		return supplierRepository.findAll();
	}

	@Override
	public Supplier findSupplierById(Long supplierId) {
		return supplierRepository.findById(supplierId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("supplierId", supplierId, "Not found"));
	}

	@Override
	public Supplier createSupplier(SupplierRequest supplierRequest) {
		Supplier supplier = new Supplier(supplierRequest.getSupplierName());
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier updateSupplier(Long supplierId, SupplierRequest supplierRequest) {
		Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
		if (supplier != null) {
			supplier.setSupplierName(supplierRequest.getSupplierName());
			supplierRepository.save(supplier);
			return supplier;
		} else {
			throw new InvalidConfigurationPropertyValueException("supplierId", supplierId, "Not Found");
		}
	}

	@Override
	public void softDeleteSupplierById(Long supplierId) {
		Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("supplierId", supplierId, "Not found"));
		supplier.setStatus(false);
		supplierRepository.save(supplier);
	}

	@Override
	public void hardDeleteSupplier(Long supplierId) {
		if (supplierRepository.findById(supplierId).get().getSupplierId().equals(supplierId)) {
			supplierRepository.deleteById(supplierId);
		} else
			throw new InvalidConfigurationPropertyValueException("supplierId", supplierId, "Not Found");
	}

	@Override
	public Supplier supplierReactivationById(Long supplierId) {
		Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("supplierId", supplierId, "Not found"));
		supplier.setStatus(true);
		return supplierRepository.save(supplier);
	}
}
