package com.NLCS.TreeShop.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.PasswordRequest;
import com.NLCS.TreeShop.payload.request.TrackingListTreeRequest;
import com.NLCS.TreeShop.payload.request.UserInformationRequest;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.security.services.TrackingListTreeService;
import com.NLCS.TreeShop.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	private static String UPLOADED_FOLDER = "F:\\NLCS\\NLCS_TreeShop_Frontend_ReactJS\\public\\assets\\images\\avatar\\";
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	TrackingListTreeService trackingListTreeService;

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<?> getAllUser() {
		try {
			List<User> listUsers = userService.getAll();
			return ResponseEntity.ok(listUsers);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
		try {
			User user = userService.findUserById(userId);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateUserInformation/{userId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<?> updateUserInformation(@PathVariable("userId") Long userId,
			@RequestBody @Valid UserInformationRequest userInformationRequest) {
		return new ResponseEntity<>(userService.updateUserInformation(userId, userInformationRequest),
				HttpStatus.CREATED);
	}

	@PostMapping(value = "/updateUserAvatar/{userId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<?> updateUserAvatar(@PathVariable("userId") Long userId,
			@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("=>><<< "+file);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		System.out.println("=>> "+fileName);
		
        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//        sanPham.setImage(file.getOriginalFilename().toString());
        Files.write(path, bytes);
		return new ResponseEntity<>(userService.updateUserAvatar(userId, file.getOriginalFilename().toString()), HttpStatus.CREATED);
//		return null;
	}

	@PutMapping(value = "/updatePassword/{userId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<?> updatePassword(@PathVariable("userId") Long userId,
			@RequestBody @Valid PasswordRequest passwordRequest) {
		try {
			return new ResponseEntity<>(userService.updatePassword(userId, passwordRequest), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("WrongPass", "Mật khẩu cũ bạn nhập vào không chính xác"));
		}
	}

	@GetMapping(value = "/trackingListTree/{userId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<?> getTrackingListTree(@PathVariable("userId") Long userId) {
		User user = userService.findUserById(userId);
		List<Tree> trees = user.getTrackingListTree();
//		Collections.sort(trees, Comparator.comparingLong(Tree::getTreeId));
		return new ResponseEntity<>(trees, HttpStatus.OK);
	}

	@PutMapping(value = "/trackingListTree", consumes = { "*/*" })
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<?> updateTreeInTrackingList(
			@RequestBody @Valid TrackingListTreeRequest trackingListTreeRequest) {
		return new ResponseEntity<>(trackingListTreeService.updateTreeInTrackingList(
				trackingListTreeRequest.getUser_id(), trackingListTreeRequest.getTree_id()), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{userId}")
	@PreAuthorize("hasRole('USER_NORMAL_ACCESS')")
	public ResponseEntity<MessageResponse> softDeleteUser(@PathVariable("userId") Long userId) {
		try {
			userService.softDeleteUser(userId);
			return ResponseEntity.ok(new MessageResponse("Dữ liệu đã bị ngắt kết nối (soft_delete)!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/{userId}")
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<MessageResponse> hardDeleteUser(@PathVariable("userId") Long userId) {
		try {
			userService.hardDeleteUser(userId);
			return ResponseEntity.ok(new MessageResponse("Đã xóa dữ liệu thành công trong CSDL!"));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/reactivation/{userId}", consumes = { "*/*" })
	@PreAuthorize("hasRole('CRUD_ALLOW_ALL')")
	public ResponseEntity<?> userReactivationById(@PathVariable("userId") Long userId) {
		return new ResponseEntity<>(userService.userReactivationById(userId), HttpStatus.CREATED);
	}
}
