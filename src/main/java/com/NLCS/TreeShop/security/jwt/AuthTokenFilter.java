package com.NLCS.TreeShop.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.NLCS.TreeShop.security.servicesImpl.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Tạo ra chuỗi jwt từ request.header = "Bearer + jwt"
		String jwt = getTokenFromRequest(request);
		
		// Tạo ra chuỗi jwt từ request.cookies
		// String jwt = parseJwt(request);

		try {
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				System.out.println("userDetails.getAuthorities(): " + userDetails.getAuthorities());

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	// Lấy ra jwt từ cookie được gửi từ Client xuống Server
//	private String parseJwt(HttpServletRequest request) {
//		String jwt = jwtUtils.getJwtFromCookies(request);
//		return jwt;
//	}

	// Lấy ra jwt từ headers gán (Bearer + jwt) được gửi từ Client xuống Server
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = jwtUtils.getTokenFromRequestHeaderBearer(request);
		return bearerToken;
	}
}
