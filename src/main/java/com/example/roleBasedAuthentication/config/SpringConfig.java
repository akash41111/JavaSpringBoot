package com.example.roleBasedAuthentication.config;

import java.security.Security;

import javax.security.sasl.AuthorizeCallback;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.builder()
								.username("admin")
								.password(passwordEncoder().encode("1234"))
								.roles("Admin")
								.build();
		
		UserDetails user = User.builder()
				.username("akash")
				.password(passwordEncoder().encode("1234"))
				.roles("User")
				.build();
		
		return new InMemoryUserDetailsManager(admin,user);
				
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		return security.csrf(csrf->csrf.disable())
							.authorizeHttpRequests(authorize->authorize
									.requestMatchers(HttpMethod.GET).hasRole("User")
//									.requestMatchers(HttpMethod.GET).hasAnyRole("Admin","User")
									
									.anyRequest()
									.authenticated())
							.httpBasic(Customizer.withDefaults())
							.build();
									
							
							
		
	}
	
}
