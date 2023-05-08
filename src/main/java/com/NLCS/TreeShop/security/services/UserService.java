package com.NLCS.TreeShop.security.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.AvatarRequest;
import com.NLCS.TreeShop.payload.request.PasswordRequest;
import com.NLCS.TreeShop.payload.request.UserInformationRequest;

@Component
public interface UserService {
	List<User> getAll();

	void softDeleteUser(Long userId);

	void hardDeleteUser(Long userId);

	User findUserById(Long userId);

	User updateUserInformation(Long userId, UserInformationRequest userInformationRequest);

	User updatePassword(Long userId, PasswordRequest passwordRequest);

	User userReactivationById(Long userId);

	User updateUserAvatar(Long userId, String avatarName);
}
