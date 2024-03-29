package com.NLCS.TreeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.ProvinceAndCity;

@Repository
public interface CityRepository extends JpaRepository<ProvinceAndCity, Long> {

}
