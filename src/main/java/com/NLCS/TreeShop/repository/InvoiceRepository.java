package com.NLCS.TreeShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	List<Invoice> findByUser_UserIdAndStatus(Long userId, boolean b);

	List<Invoice> findByUser_UserIdAndStatusAndWasPay(Long userId, boolean b, boolean c);

	Invoice findByUser_UserIdAndWasPay(Long user_id, boolean b);
}
