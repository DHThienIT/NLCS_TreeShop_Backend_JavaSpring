package com.NLCS.TreeShop.security.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import com.NLCS.TreeShop.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${bezkoder.app.jwtCookieName}")
	private String jwtCookie;

	// Lấy ra jwt từ cookie được gửi từ Client xuống Server
//	public String getJwtFromCookies(HttpServletRequest request) {
//		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
//		if (cookie != null) {
//			return cookie.getValue();
//		} else {
//			return null;
//		}
//	}

	// Lấy ra jwt từ headers gán (Bearer + jwt) được gửi từ Client xuống Server
	public String getTokenFromRequestHeaderBearer(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// Tạo ra Token từ username được đưa vào
	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()) // Thời gian Token được tạo ra
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Thời gian Token hết hạng
				.signWith(SignatureAlgorithm.HS512, jwtSecret) // Chữ ký
				.compact();
	}

	// trả về ResponseCookie từ UserDetailsImpl được đưa vào
	public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {

		// Tạo ra chuỗi Jwt từ userPrincipal.getUsername()
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());

		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true)
				.build();
		return cookie;
	}

	// Xóa jwt trong Cookie đã tạo ra
	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
		return cookie;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
