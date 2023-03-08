package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
