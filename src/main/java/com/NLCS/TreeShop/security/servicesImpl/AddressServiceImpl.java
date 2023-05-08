package com.NLCS.TreeShop.security.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Address;
import com.NLCS.TreeShop.models.ProvinceAndCity;
import com.NLCS.TreeShop.models.CountryAndDistrict;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.models.Ward;
import com.NLCS.TreeShop.payload.request.AddressRequest;
import com.NLCS.TreeShop.payload.request.SetDefaultAddressRequest;
import com.NLCS.TreeShop.repository.AddressRepository;
import com.NLCS.TreeShop.repository.CityRepository;
import com.NLCS.TreeShop.repository.DistrictRepository;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.repository.WardRepository;
import com.NLCS.TreeShop.security.services.AddressService;

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
		CountryAndDistrict district = districtRepository.findById(addressRequest.getCountryAndDistrict_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("District_id",
						addressRequest.getCountryAndDistrict_id(), "Not found"));
		ProvinceAndCity city = cityRepository.findById(addressRequest.getProvinceAndCity_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("City_id",
						addressRequest.getProvinceAndCity_id(), "Not found"));
		User user = userRepository.findById(addressRequest.getUser_id())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("User_id",
						addressRequest.getUser_id(), "Not found"));
		Address addressWasSetDefault = addressRepository.findByUser_UserIdAndSetDefault(addressRequest.getUser_id(), true);
		
		boolean setDefault = false;
		if (user.getAddresses().isEmpty()) {
			System.out.println("123");
			setDefault = true;
		}
		else {
			System.out.println(addressRequest.getSetDefault());
			if(addressRequest.getSetDefault() == true) {
				addressWasSetDefault.setDefault(false);
				addressRepository.save(addressWasSetDefault);
				setDefault = true;
			}
		}
		Address address = new Address(addressRequest.getRecipientName(), addressRequest.getSpecificAddress(),
				addressRequest.getPhone(), ward, district, city, user, setDefault);
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
			CountryAndDistrict district = districtRepository.findById(addressRequest.getCountryAndDistrict_id())
					.orElseThrow(() -> new InvalidConfigurationPropertyValueException("District_id",
							addressRequest.getCountryAndDistrict_id(), "Not found"));
			ProvinceAndCity city = cityRepository.findById(addressRequest.getProvinceAndCity_id())
					.orElseThrow(() -> new InvalidConfigurationPropertyValueException("City_id",
							addressRequest.getProvinceAndCity_id(), "Not found"));

			address.setName(addressRequest.getRecipientName());
			address.setSpecificAddress(addressRequest.getSpecificAddress());
			address.setPhone(addressRequest.getPhone());
			address.setCountryAndDistrict(district);
			address.setWard(ward);
			address.setProvinceAndCity(city);
			addressRepository.save(address);
		} else {
			throw new InvalidConfigurationPropertyValueException("addressId", addressId, "Not found");
		}

		return address;
	}

	@Override
	public void setDefaultAddress(SetDefaultAddressRequest setDefaultAddressRequest) {
		Address addressWasSetDefault = addressRepository.findById(setDefaultAddressRequest.getAddressIdWasSetDefault())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("addressId",
						setDefaultAddressRequest.getAddressIdWasSetDefault(), "Not found"));
		addressWasSetDefault.setDefault(false);
		Address addressWillSetDefault = addressRepository
				.findById(setDefaultAddressRequest.getAddressIdWillSetDefault())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("addressId",
						setDefaultAddressRequest.getAddressIdWillSetDefault(), "Not found"));
		addressWillSetDefault.setDefault(true);
		addressRepository.save(addressWasSetDefault);
		addressRepository.save(addressWillSetDefault);
	}

	@Override
	public List<ProvinceAndCity> getAllProvinceAndCity() {
		List<ProvinceAndCity> listCity = cityRepository.findAll();
		return listCity;
	}

	@Override
	public List<CountryAndDistrict> getAllCountyAndDistrict() {
		List<CountryAndDistrict> listDistrict = districtRepository.findAll();
		return listDistrict;
	}

	@Override
	public List<Ward> getAllWards() {
		List<Ward> listWard = wardRepository.findAll();
		return listWard;
	}
}
