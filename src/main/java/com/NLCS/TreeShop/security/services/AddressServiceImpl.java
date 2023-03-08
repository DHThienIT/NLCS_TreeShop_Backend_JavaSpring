package com.NLCS.TreeShop.security.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.models.City;
import com.NLCS.TreeShop.models.District;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.models.Ward;
import com.NLCS.TreeShop.payload.request.AddressRequest;
import com.NLCS.TreeShop.repository.AddressRepository;
import com.NLCS.TreeShop.repository.CityRepository;
import com.NLCS.TreeShop.repository.DistrictRepository;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.repository.WardRepository;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	WardRepository wardRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	CityRepository cityRepository;

	@Override
	public List<Address> getAllAddressByUser(Long userId) {
		return addressRepository.findByUser_UserIdAndStatus(userId, true);
	}

	@Override
	public Address findAddressById(Long addressId) {
		return addressRepository.findById(addressId).orElseThrow();
	}

	@Override
	public void hardDeleteAddressById(Long addressId) {
		if (addressRepository.findById(addressId).get().getAddressId().equals(addressId)) {
			addressRepository.deleteById(addressId);
		} else
			throw new InvalidConfigurationPropertyValueException("invoiceId", addressId, "Not Found");
	}

	@Override
	public void softDeleteAddressById(Long addressId) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("addressId", addressId, "Not found"));
		address.setStatus(false);
		addressRepository.save(address);
	}

	@Override
	public Address createAddress(AddressRequest addressRequest) {
		Ward ward = wardRepository.findById(addressRequest.getWard_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Ward_id",
						addressRequest.getWard_id(), "Not found"));
		District district = districtRepository.findById(addressRequest.getDistrict_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("District_id",
						addressRequest.getDistrict_id(), "Not found"));
		City city = cityRepository.findById(addressRequest.getCity_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("City_id",
						addressRequest.getCity_id(), "Not found"));
		User user = userRepository.findById(addressRequest.getUser_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("User_id",
						addressRequest.getUser_id(), "Not found"));

		Address address = new Address(addressRequest.getAddressName(), addressRequest.getPhone(), ward, district, city,
				user);
		addressRepository.save(address);
		return address;
	}

	@Override
	public Address updateAddress(Long addressId, AddressRequest addressRequest) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("addressId", addressId, "Not found"));
		if (address != null) {
			Ward ward = wardRepository.findById(addressRequest.getWard_id())
					.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Ward_id",
							addressRequest.getWard_id(), "Not found"));
			District district = districtRepository.findById(addressRequest.getDistrict_id())
					.orElseThrow(() -> new InvalidConfigurationPropertyValueException("District_id",
							addressRequest.getDistrict_id(), "Not found"));
			City city = cityRepository.findById(addressRequest.getCity_id())
					.orElseThrow(() -> new InvalidConfigurationPropertyValueException("City_id",
							addressRequest.getCity_id(), "Not found"));

			address.setAddress(addressRequest.getAddressName());
			address.setPhone(addressRequest.getPhone());
			address.setDistrict(district);
			address.setWard(ward);
			address.setCity(city);
			addressRepository.save(address);
		} else {
			throw new InvalidConfigurationPropertyValueException("addressId", addressId, "Not found");
		}

		return address;
	}
}
