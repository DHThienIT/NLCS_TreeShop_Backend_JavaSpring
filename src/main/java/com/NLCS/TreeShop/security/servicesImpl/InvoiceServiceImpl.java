package com.NLCS.TreeShop.security.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.models.CartItem;
import com.NLCS.TreeShop.models.Invoice;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.InvoiceRequest;
import com.NLCS.TreeShop.repository.AddressRepository;
import com.NLCS.TreeShop.repository.CartItemRepository;
import com.NLCS.TreeShop.repository.InvoiceRepository;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.security.services.CartItemService;
import com.NLCS.TreeShop.security.services.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	CartItemService cartItemService;
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired
	TreeRepository treeRepository;

	@Override
	public Invoice creatInvoice(InvoiceRequest invoiceRequest) {
		double totalPrice = 0;
		Invoice invoice0 = invoiceRepository.findByUser_UserIdAndWasPay(invoiceRequest.getUser_id(), false);
		User user = userRepository.findById(invoiceRequest.getUser_id()).orElseThrow();
		Address address = addressRepository.findById(invoiceRequest.getAddress_id()).orElseThrow();
		
		List<CartItem> cartItems = cartItemService.getCart(invoiceRequest.getUser_id());
		
		Double shipmentFee = invoiceRequest.getShipmentFee();
		Double promotionPrice = invoiceRequest.getPromotionPrice();
		if(shipmentFee.toString().isBlank()) shipmentFee=0.0;
		if(promotionPrice.toString().isBlank()) promotionPrice=0.0;
		
		for (CartItem cartItem : cartItems) {
			totalPrice += cartItem.getQuantity() * cartItem.getTree().getPrice();
		}
		totalPrice = totalPrice + shipmentFee + promotionPrice;
		
		if(cartItems.isEmpty()) throw new InvalidConfigurationPropertyValueException("Giỏ hàng", cartItems, "Giỏ hàng hiện đang rỗng!");
		
		if(invoice0!=null) {
			invoice0.setAddress(address);
			invoice0.setTotalPrice(totalPrice);
			invoice0.setPromotionPrice(promotionPrice);
			invoice0.setShipmentFee(shipmentFee);
			invoiceRepository.save(invoice0);
			
			for (CartItem cartItem : cartItems) {
				cartItem.setInvoice(invoice0);
				cartItemRepository.save(cartItem);
			}
			
			return invoice0;
		} else {
			Invoice invoice = new Invoice(user, "", "", totalPrice, false, cartItems, address, shipmentFee, promotionPrice);
			//timeCreate & paymentMethod gán "" vì chưa thanh toán thành công nên chưa thể tạo.
			
			invoiceRepository.save(invoice);
			totalPrice = 0;

			for (CartItem cartItem : cartItems) {
				cartItem.setInvoice(invoice);
				cartItemRepository.save(cartItem);
			}

			return invoice;
		}
	}

	@Override
	public Invoice getInvoice(Long invoiceId) {
		return invoiceRepository.findById(invoiceId).orElseThrow();
	}

	@Override
	public List<Invoice> getAllInvoices() {
		return invoiceRepository.findAll();
	}

	@Override
	public List<Invoice> getAllUserInvoices(Long userId) {
		return invoiceRepository.findByUser_UserIdAndStatus(userId, true);
	}
	
	@Override
	public List<Invoice> getAllInvoicesPaySuccessByUser(Long userId) {
		return invoiceRepository.findByUser_UserIdAndStatusAndWasPay(userId, true, true);
	}

	@Override
	public void softDeleteInvoiceById(Long invoiceId) {
		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
		if (invoiceRepository.findById(invoiceId).get().getInvoiceId().equals(invoiceId)) {
			invoice.setStatus(false);
			invoiceRepository.save(invoice);
		} else
			throw new InvalidConfigurationPropertyValueException("invoiceId", invoiceId, "Not Found");
	}
	
	@Override
	public void hardDeleteInvoiceById(Long invoiceId) {
		
		if (invoiceRepository.findById(invoiceId).get().getInvoiceId().equals(invoiceId)) {
			invoiceRepository.deleteById(invoiceId);
		} else
			throw new InvalidConfigurationPropertyValueException("invoiceId", invoiceId, "Not Found");
	}
	
	@Override
	public Optional<Invoice> setPaymentSuccess(Invoice invoice, String paymentMethod, Long userId) {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		String timeString = day + "-" + month + "-" + year + " " + hour + ":" + minute;
		
		invoice.setPaymentMethod(paymentMethod);
		invoice.setWasPay(true);
		invoice.setTimeCreate(timeString);
		invoiceRepository.save(invoice);
		
		List<CartItem> listCartItem = invoice.getCartItems();
		Tree tree = new Tree();
		
		for (CartItem cartItem : listCartItem) {
			cartItem.setStatusPay(true);
			cartItemRepository.save(cartItem);
			
			tree = cartItem.getTree();
			tree.setStock(tree.getStock() - cartItem.getQuantity());
			treeRepository.save(tree);
		}
		
		return Optional.of(invoice);
	}

	@Override
	public Invoice invoiceReactivationById(Long invoiceId) {
		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("invoiceId", invoiceId, "Not found"));
		invoice.setStatus(true);
		return invoiceRepository.save(invoice);
	}
}
