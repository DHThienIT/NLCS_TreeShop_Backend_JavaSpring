package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.models.ProvinceAndCity;
import com.NLCS.TreeShop.models.CountryAndDistrict;
import com.NLCS.TreeShop.models.Ward;
import com.NLCS.TreeShop.payload.request.AddressRequest;
import com.NLCS.TreeShop.payload.request.SetDefaultAddressRequest;

@Component
public interface AddressService {

	List<Address> getAllAddressByUser(Long userId);

	Address findAddressById(Long addressId);

	void hardDeleteAddressById(Long addressId);

	void softDeleteAddressById(Long addressId);

	Address createAddress(AddressRequest addressRequest);

	Address updateAddress(Long addressId, AddressRequest addressRequest);
	
	void setDefaultAddress(SetDefaultAddressRequest setDefaultAddressRequest);

	List<ProvinceAndCity> getAllProvinceAndCity();

	List<CountryAndDistrict> getAllCountyAndDistrict();

	List<Ward> getAllWards();
}
