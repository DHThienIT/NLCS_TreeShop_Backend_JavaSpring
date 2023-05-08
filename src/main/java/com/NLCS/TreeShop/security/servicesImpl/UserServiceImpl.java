package com.NLCS.TreeShop.security.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.AvatarRequest;
import com.NLCS.TreeShop.payload.request.PasswordRequest;
import com.NLCS.TreeShop.payload.request.UserInformationRequest;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.security.services.UserService;

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
			user.setPhone(userInformationRequest.getPhone());
		} else {
			throw new InvalidConfigurationPropertyValueException("user", user, "Not found");
		}
		return userRepository.save(user);
	}
	
	@Override
	public User updateUserAvatar(Long userId, String avatarName) {
		User user = userRepository.findById(userId).orElseThrow();
		if (!user.equals(null)) {
			user.setUserPhoto(avatarName);
		} else {
			throw new InvalidConfigurationPropertyValueException("user", user, "Not found");
		}
		return userRepository.save(user);
	}

	@Override
	public User updatePassword(Long userId, PasswordRequest passwordRequest) {
		User user = userRepository.findById(userId).orElseThrow();
		if (!user.equals(null)) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = user.getPassword(); // Lấy encodedPassword từ cơ sở dữ liệu
//			
			System.out.println("sString2: " + encoder.encode(passwordRequest.getNewPassword()));

			if (passwordEncoder.matches(passwordRequest.getOldPassword(), encodedPassword)) {
				System.out.println("Mật khẩu hợp lệ");// Mật khẩu hợp lệ
				user.setPassword(encoder.encode(passwordRequest.getNewPassword()));
				userRepository.save(user);
			} else {
				System.out.println("Mật khẩu không hợp lệ");// Mật khẩu không hợp lệ
				throw new InvalidConfigurationPropertyValueException("Old password in ", user, "and new password you entered doesn't same!");
			}
		} else {
			throw new InvalidConfigurationPropertyValueException("user", user, "Not found");
		}
		return user;
	}

	@Override
	public User userReactivationById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("userId", userId, "Not found"));
		user.setStatus(true);
		return userRepository.save(user);
	}

}
