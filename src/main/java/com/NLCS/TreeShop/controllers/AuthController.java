package com.NLCS.TreeShop.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NLCS.TreeShop.models.ERole;
import com.NLCS.TreeShop.models.Role;
import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.payload.request.LoginRequest;
import com.NLCS.TreeShop.payload.request.SignupRequest;
import com.NLCS.TreeShop.payload.response.JwtResponse;
import com.NLCS.TreeShop.payload.response.MessageResponse;
import com.NLCS.TreeShop.repository.RoleRepository;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.security.jwt.JwtUtils;
import com.NLCS.TreeShop.security.services.UserDetailsImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getUsername());
		System.out.println(loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		System.out.println("Authentication: " + authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userRepository.findById(userDetails.getId()).orElseThrow();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

//		System.out.println("SecurityContextHolder: " + SecurityContextHolder.getContext());
//		System.out.println("jwt: " + jwt);
//		System.out.println("userDetails: " + userDetails);
//		System.out.println("user: " + user);
//		System.out.println("jwtCookie: " + jwtCookie);

		List<String> privileges = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

//		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//				.body(new JwtResponse(jwt, userDetails.getId(), user.getFirstname(), user.getLastname(),
//						userDetails.getUsername(), userDetails.getEmail(), privileges));
//		System.out.println("OK");
		return ResponseEntity.ok(new JwtResponse(jwt, user.getUserId(), user.getFirstname(), user.getLastname(),
				user.getUsername(), user.getEmail(), privileges));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		List<Tree> trackingListTree = new ArrayList<>();
		String phone = "none";
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username", "Tên đăng nhập đã tồn tại!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email", "Email này đã đăng ký tài khoản!"));
		}

		User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getEmail(), phone,
				signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), trackingListTree);

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;

				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;

				case "user":
					Role userRole1 = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole1);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Message", "Bạn đã đăng ký tài khoản thành công!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("Message", "Bạn đã thoát đăng nhập!"));
	}
}
