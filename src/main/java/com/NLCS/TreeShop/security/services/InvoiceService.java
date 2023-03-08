package com.NLCS.TreeShop.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.models.Invoice;
import com.NLCS.TreeShop.payload.request.InvoiceRequest;

@Component
public interface InvoiceService {
	Invoice getInvoice(Long invoiceId);

	List<Invoice> getAllInvoices();

	List<Invoice> getAllUserInvoices(Long userId);

	List<Invoice> getAllInvoicesPaySuccessByUser(Long userId);

	Invoice creatInvoice(InvoiceRequest invoiceRequest);

	void softDeleteInvoiceById(Long invoiceId);

	void hardDeleteInvoiceById(Long invoiceId);

	Optional<Invoice> updateProductsInInvoice(Invoice invoice, Long userId);

	Optional<Invoice> updateAddressInInvoice(Invoice invoice, Address address);

	Optional<Invoice> setPaymentSuccess(Invoice invoice, String paymentMethod, Long userId);

	Invoice invoiceReactivationById(Long invoiceId);

}
