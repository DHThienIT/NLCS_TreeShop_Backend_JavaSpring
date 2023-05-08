package com.NLCS.TreeShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findById(Long id);

	boolean existsBySpecificAddress(String specificAddress);

	List<Address> findByUser_UserIdAndStatus(Long userId, boolean status);

	Address findByUser_UserIdAndSetDefault(Long user_id, boolean b);
}
