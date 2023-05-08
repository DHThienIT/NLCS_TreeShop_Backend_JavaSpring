package com.NLCS.TreeShop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.NLCS.TreeShop.security.jwt.AuthEntryPointJwt;
import com.NLCS.TreeShop.security.jwt.AuthTokenFilter;
import com.NLCS.TreeShop.security.servicesImpl.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(
		prePostEnabled = true)
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	//Bộ lọc xác thực Token
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	// @Override
	// public void configure(AuthenticationManagerBuilder
	// authenticationManagerBuilder) throws Exception {
	// authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	// }

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);		// kq userDetailsServiceImpl: 1 UserDetails hoặc UsernameNotFoundException
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	// @Bean
	// @Override
	// public AuthenticationManager authenticationManagerBean() throws Exception {
	// return super.authenticationManagerBean();
	// }

	//Trả về AuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.cors().and().csrf().disable()
	// .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	// .authorizeRequests().antMatchers("/api/auth/**").permitAll()
	// .antMatchers("/api/test/**").permitAll()
	// .anyRequest().authenticated();
	//
	// http.addFilterBefore(authenticationJwtTokenFilter(),
	// UsernamePasswordAuthenticationFilter.class);
	// }
	
//	@Override
//	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	    // Cấu hình xác thực bằng tài khoản trong database hoặc từ một nguồn nào đó
//	    auth.jdbcAuthentication()
//	      .dataSource(dataSource)
//	      .passwordEncoder(passwordEncoder())
//	      .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
//	      .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?");
//	  }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				//Xử lý ngoại lệ
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/tree/**").permitAll()
				.antMatchers("/api/cart/**").permitAll()
				.antMatchers("/api/search/**").permitAll()
				.antMatchers("/api/invoice/**").permitAll()
				.antMatchers("/api/test/**").permitAll()
				.antMatchers("/api/user/**").permitAll()
				.antMatchers("/api/address/**").permitAll()
				.antMatchers("/api/category/**").permitAll()
				.antMatchers("/api/payment/**").permitAll()
				.antMatchers("/api/supplier/**").permitAll()
				.antMatchers("/api/promotion/**").permitAll()
				.antMatchers("/api/deliveryMethod/**").permitAll()
		.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
