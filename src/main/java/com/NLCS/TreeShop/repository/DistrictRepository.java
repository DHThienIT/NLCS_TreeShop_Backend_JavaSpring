package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

}
