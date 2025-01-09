package com.org.mfm.config;

import static com.org.mfm.enums.Permissions.ADMIN_CREATE;
import static com.org.mfm.enums.Permissions.ADMIN_DELETE;
import static com.org.mfm.enums.Permissions.ADMIN_READ;
import static com.org.mfm.enums.Permissions.ADMIN_UPDATE;
import static com.org.mfm.enums.Permissions.MANAGER_CREATE;
import static com.org.mfm.enums.Permissions.MANAGER_DELETE;
import static com.org.mfm.enums.Permissions.MANAGER_READ;
import static com.org.mfm.enums.Permissions.MANAGER_UPDATE;
import static com.org.mfm.enums.Role.ADMIN;
import static com.org.mfm.enums.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.org.mfm.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {

	public static final String LOGIN_URL = "/login";
	public static final String LOGOUT_URL = "/api/v1/auth/logout";
	public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";

	public static final String DEFAULT_SUCCESS_URL = "/home";
	public static final String MANAGEMENT = "/api/v1/management/**";
	public static final String ADMIN_URL = "/api/v1/admin/**";
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers("/user/authenticate/**", "/user/create/**", "/user/refresh-token/**",
								"/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
								"/swagger-resources/**", "/configuration/ui", "/configuration/security",
								"/swagger-ui/**", "/webjars/**", "/swagger-ui.html")
						.permitAll()

						.requestMatchers(MANAGEMENT).hasAnyRole(ADMIN.name(), MANAGER.name())
						.requestMatchers(GET, MANAGEMENT).hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
						.requestMatchers(POST, MANAGEMENT).hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
						.requestMatchers(PUT, MANAGEMENT).hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
						.requestMatchers(DELETE, MANAGEMENT).hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

						.requestMatchers(ADMIN_URL).hasRole(ADMIN.name()).requestMatchers(GET, ADMIN_URL)
						.hasAuthority(ADMIN_READ.name()).requestMatchers(POST, ADMIN_URL)
						.hasAuthority(ADMIN_CREATE.name()).requestMatchers(PUT, ADMIN_URL)
						.hasAuthority(ADMIN_UPDATE.name()).requestMatchers(DELETE, ADMIN_URL)
						.hasAuthority(ADMIN_DELETE.name())

						.anyRequest().authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout.logoutUrl(LOGOUT_URL).addLogoutHandler(logoutHandler).logoutSuccessHandler(
						(request, response, authentication) -> SecurityContextHolder.clearContext()));

		return http.build();
	}

}