package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.CountryAndDistrict;

@Repository
public interface DistrictRepository extends JpaRepository<CountryAndDistrict, Long> {

}
