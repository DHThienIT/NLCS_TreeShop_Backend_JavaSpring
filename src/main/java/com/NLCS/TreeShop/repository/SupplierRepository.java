package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
