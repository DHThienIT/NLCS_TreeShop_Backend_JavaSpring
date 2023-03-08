package com.NLCS.TreeShop.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Supplier;
import com.NLCS.TreeShop.models.Category;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.PasswordRequest;
import com.NLCS.TreeShop.payload.request.TreeRequest;
import com.NLCS.TreeShop.payload.request.UserInformationRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.payload.response.TreeResponse;
import com.NLCS.TreeShop.repository.SupplierRepository;
import com.NLCS.TreeShop.repository.CategoryRepository;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.repository.UserRepository;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("UserId", userId, "Not found"));
		return user;
	}

	@Override
	public void softDeleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		if (user.getUserId().equals(userId)) {
			user.setStatus(false);
			userRepository.save(user);
		} else
			throw new InvalidConfigurationPropertyValueException("userId", userId, "Not found");
	}

	@Override
	public void hardDeleteUser(Long userId) {
		if (userRepository.findById(userId).get().getUserId().equals(userId)) {
			userRepository.deleteById(userId);
		} else
			throw new InvalidConfigurationPropertyValueException("userId", userId, "Not found");
	}

	@Override
	public User updateUserInformation(Long userId, UserInformationRequest userInformationRequest) {
		User user = userRepository.findById(userId).orElseThrow();
		if (!user.equals(null)) {
			user.setFirstname(userInformationRequest.getFirstname());
			user.setLastname(userInformationRequest.getLastname());
			user.setEmail(userInformationRequest.getEmail());
			user.setUserPhoto(userInformationRequest.getUserPhoto());
		} else {
			throw new InvalidConfigurationPropertyValueException("user", user, "Not found");
		}
		return userRepository.save(user);
	}

	@Override
	public User updatePassword(Long userId, PasswordRequest passwordRequest) {
		User user = userRepository.findById(userId).orElseThrow();
		if (!user.equals(null)) {
			user.setPassword(encoder.encode(passwordRequest.getPassword()));
		} else {
			throw new InvalidConfigurationPropertyValueException("user", user, "Not found");
		}
		return userRepository.save(user);
	}

	@Override
	public User userReactivationById(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("userId", userId, "Not found"));
		user.setStatus(true);
		return userRepository.save(user);
	}

}
