package com.NLCS.TreeShop.security.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Supplier;
import com.NLCS.TreeShop.payload.request.SupplierRequest;

@Component
public interface SupplierService {

	List<Supplier> getAll();

	Supplier findSupplierById(Long supplierId);

	Supplier createSupplier(SupplierRequest supplierRequest);

	Supplier updateSupplier(Long supplierId, SupplierRequest supplierRequest);

	void softDeleteSupplierById(Long supplierId);

	void hardDeleteSupplier(Long supplierId);

	Supplier supplierReactivationById(Long supplierId);
}
