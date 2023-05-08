package com.NLCS.TreeShop.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NLCS.TreeShop.config.PaypalPaymentIntent;
import com.NLCS.TreeShop.config.PaypalPaymentMethod;
import com.NLCS.TreeShop.config.VnpayConfig;
import com.NLCS.TreeShop.models.EPaymentMethod;
import com.NLCS.TreeShop.models.Invoice;
import com.NLCS.TreeShop.payload.request.PaymentRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.InvoiceRepository;
import com.NLCS.TreeShop.security.services.InvoiceService;
import com.NLCS.TreeShop.security.services.PaypalService;
import com.NLCS.TreeShop.utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	InvoiceService invoiceService;

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaypalService paypalService;

	@GetMapping("/getAllPaymentMethod")
	public ResponseEntity<?> getAllPaymentMethod() {
		EPaymentMethod[] paymentMethods = EPaymentMethod.values();
		return ResponseEntity.ok(paymentMethods);
	}

	Invoice invoice0 = new Invoice();
	Long userId0 = 0L;

	@PostMapping("/pay/{invoiceId}")
	@PreAuthorize("hasRole('PAYMENT_NORMAL_ACCESS')")
	public ResponseEntity<?> pay(HttpServletRequest req, @PathVariable("invoiceId") Long invoiceId,
			@Valid @RequestBody PaymentRequest paymentRequest) throws ServletException, IOException {

		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();
		invoice0 = invoice;
		userId0 = paymentRequest.getUser_id();

		if (paymentRequest.getPaymentMethod().equals("VnPay")) {
			String vnp_Version = "2.1.0";
			String vnp_Command = "pay";
			String vnp_OrderInfo = "Thanh toan don hang";
			String orderType = "topup";
			String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
			String vnp_IpAddr = VnpayConfig.getIpAddress(req);
			String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

			Map vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", vnp_Version);
			vnp_Params.put("vnp_Command", vnp_Command);
			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
			vnp_Params.put("vnp_Amount", String.valueOf((int) invoice.getTotalPrice() * 100));
			vnp_Params.put("vnp_CurrCode", "VND");

			String bank_code = "";
			if (bank_code != null && !bank_code.isEmpty()) {
				vnp_Params.put("vnp_BankCode", bank_code);
			}

			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
			vnp_Params.put("vnp_OrderType", orderType);

			String locate = "vn";
			vnp_Params.put("vnp_Locale", "vn");

			vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(cld.getTime());

			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
			cld.add(Calendar.MINUTE, 15);
			String vnp_ExpireDate = formatter.format(cld.getTime());
			// Add Params of 2.1.0 Version
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			// Build data to hash and querystring
			List fieldNames = new ArrayList(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			Iterator itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}
			String queryUrl = query.toString();
			String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;

			invoice0.setPaymentMethod(paymentRequest.getPaymentMethod());
			System.out.println(paymentUrl);

			return ResponseEntity.ok(new MessageResponse("Success", paymentUrl));
		} else if (paymentRequest.getPaymentMethod().equals("Paypal")) {
			String cancelUrl = Utils.getBaseURL(req) + "/api/payment/paypalError";
			String successUrl = Utils.getBaseURL(req) + "/api/payment/paypalSuccess";
			Double USD = 23465.0;

			try {
				Payment payment = paypalService.createPayment(invoice.getTotalPrice() / USD, "USD",
						PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale, "payment description 123456", cancelUrl,
						successUrl);
				for (Links links : payment.getLinks()) {
					if (links.getRel().equals("approval_url")) {
						return ResponseEntity.ok(new MessageResponse("Success", links.getHref()));
					}
				}
			} catch (PayPalRESTException e) {
				log.error(e.getMessage());
			}

			return ResponseEntity.ok(new MessageResponse("PayPaypalSuccess", "Thanh toán thành công!"));
		} else if (paymentRequest.getPaymentMethod().equals("PayDirect")) {
			invoiceService.setPaymentSuccess(invoice, paymentRequest.getPaymentMethod(), paymentRequest.getUser_id());
			return ResponseEntity.ok(new MessageResponse("PayDirectSuccess", "Thanh toán thành công!"));
		} else {
			return ResponseEntity.ok(new MessageResponse("Error", "Tìm không thấy phương thức thanh toán đã đưa vào!"));
		}
	}

	@GetMapping("/paypalError")
	public void cancelPay(HttpServletResponse response) throws IOException {
		String redirectUrlIfError = "http://localhost:8081/paymentResultNotificationError";
		response.sendRedirect(redirectUrlIfError);
	}

	@GetMapping("/paypalSuccess")
	public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			HttpServletResponse response) throws ServletException, IOException {
		String redirectUrlData = "http://localhost:8081/data";
		String redirectUrlIfSuccess = "http://localhost:8081/paymentResultNotificationSuccessPayPaypal";
		String redirectUrlIfError = "http://localhost:8081/paymentResultNotificationError";
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				System.out.println("xxxxxxxxxxxxxxxxxxxx");
				Invoice invoice = invoiceRepository.findById(invoice0.getInvoiceId()).orElseThrow();
				try {
					invoiceService.setPaymentSuccess(invoice, "Paypal", userId0);
					response.sendRedirect(redirectUrlIfSuccess);
				} catch (Exception e) {
					response.sendRedirect(redirectUrlIfError);
				}
			}
		} catch (PayPalRESTException e) {
			response.sendRedirect(redirectUrlIfError);
			log.error(e.getMessage());
		}
	}

	@GetMapping("/returnFromVnpay")
	public ResponseEntity<Invoice> returnFromVnpay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Đã vào đây thành công!");

		String redirectUrlIfError = "http://localhost:8081/paymentResultNotificationError";
		String redirectUrlIfSuccess = "http://localhost:8081/paymentResultNotificationSuccessPayVnpay";

		try {
			Map fields = new HashMap();
			for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
				String fieldName = URLEncoder.encode((String) params.nextElement(),
						StandardCharsets.US_ASCII.toString());
				String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
						StandardCharsets.US_ASCII.toString());
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					fields.put(fieldName, fieldValue);
				}
			}

			String vnp_SecureHash = request.getParameter("vnp_SecureHash");
			if (fields.containsKey("vnp_SecureHashType")) {
				fields.remove("vnp_SecureHashType");
			}
			if (fields.containsKey("vnp_SecureHash")) {
				fields.remove("vnp_SecureHash");
			}

			// Check checksum
			String signValue = VnpayConfig.hashAllFields(fields);
			if (signValue.equals(vnp_SecureHash)) {
				boolean checkOrderId = true; // vnp_TxnRef exists in your database
				boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the
											// amount of the code (vnp_TxnRef) in the Your database).
				boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)

				if (checkOrderId) {
					if (checkAmount) {
						if (checkOrderStatus) {
							if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
//	=> giao dịch thành công
								System.out.println("Đã vào 1, giao dịch thành công!");
								// Here Code update PaymnentStatus = 1 into your Database
								Invoice invoice = invoiceRepository.findById(invoice0.getInvoiceId()).orElseThrow();
								try {
									invoiceService.setPaymentSuccess(invoice, invoice0.getPaymentMethod(), userId0);
									response.sendRedirect(redirectUrlIfSuccess);
									return new ResponseEntity<>(null, HttpStatus.OK);
								} catch (Exception e) {
									response.sendRedirect(redirectUrlIfError);
									return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
								}
							} else {
//	=> giao dịch thất bại
								System.out.println("Đã vào 2, giao dịch thất bại!");
								// Here Code update PaymnentStatus = 2 into your Database
								System.out.println("Tại chỗ này gán lệnh trả về trang giao dịch thất bại!");
								response.sendRedirect(redirectUrlIfError);
								return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
							}
						} else {

							System.out.print("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
						}
					} else {
						System.out.print("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
					}
				} else {
					System.out.print("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
				}
			} else {
				System.out.print("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
			}
		} catch (Exception e) {
			System.out.print("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
		}
		response.sendRedirect(redirectUrlIfError);
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
