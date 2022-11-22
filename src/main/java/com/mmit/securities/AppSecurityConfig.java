package com.mmit.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.mmit.model.entity.UserRole;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Autowired
	private AuthenticationSuccessHandler userAuthenticationSuccessHandler;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean 
	public UserDetailService userDetailService()
	{
		return new UserDetailService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticatedProvider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http	
			.authorizeHttpRequests()
			.antMatchers("/"
					, "/shop"
					, "/shop/login"
					, "/shop/register"
					, "/shop/product/**"
					, "/shop/category/**"
					, "/shop/user/register"
					, "/shop/user/add"
					, "/chat/load-offline-msg"
					, "/admin/**"
					, "/cart/detail"
					, "/css/**"
					, "/js/**"
					, "/images/**"
					, "/app/**"
					, "/fonts/**"
					, "/uploads/**")
			.permitAll()
			.antMatchers("/cart/checkout", "/cart/place-order")
			.hasAnyRole("admin", "customer")

			.antMatchers("/admin", 
					"/dashboard", 
					"/categories", 
					"/category/add", 
					"/category/edit/{id}", 
					"/category/save",
					"/user/save",
					"/products",
					"/product/add",
					"/product/edit/{id}",
					"/product/save",
					"/orders",
					"/order/detail/{id}",
					"/orders/update-status",
					"/order/delete",
					"/customers",
					"/customer/detail/{id}")
			.hasRole("admin")
			.antMatchers("/shop/about")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/shop/login")
			.successHandler(userAuthenticationSuccessHandler)
			.permitAll()
			.and()
			.logout()
			.logoutSuccessHandler(logoutSuccessHandler)
			.permitAll();
		return http.build();
	}
}
