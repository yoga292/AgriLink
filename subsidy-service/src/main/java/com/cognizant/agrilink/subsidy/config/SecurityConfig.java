package com.cognizant.agrilink.subsidy.config;

import com.cognizant.agrilink.subsidy.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// audit-logs
						.requestMatchers(HttpMethod.GET, "/audit-logs", "/audit-logs/**")
						.hasAnyRole("ComplianceAnalyst", "AgriLinkAdmin")
						// scheme-catalogs (Farmer NO access)
						.requestMatchers(HttpMethod.GET, "/scheme-catalogs", "/scheme-catalogs/**")
								.hasAnyRole("ExtensionOfficer", "ProcurementOfficer", "SubsidyAdmin",
										"ComplianceAnalyst", "AgriLinkAdmin")
						.requestMatchers(HttpMethod.POST, "/scheme-catalogs")
								.hasAnyRole("SubsidyAdmin", "AgriLinkAdmin")
						.requestMatchers(HttpMethod.PUT, "/scheme-catalogs/**")
								.hasAnyRole("SubsidyAdmin", "AgriLinkAdmin")
						.requestMatchers(HttpMethod.DELETE, "/scheme-catalogs/**")
								.hasAnyRole("SubsidyAdmin", "AgriLinkAdmin")
						// subsidy-applications (Procurement NO access)
						.requestMatchers(HttpMethod.GET, "/subsidy-applications", "/subsidy-applications/**")
								.hasAnyRole("Farmer", "ExtensionOfficer", "SubsidyAdmin",
										"ComplianceAnalyst", "AgriLinkAdmin")
						.requestMatchers(HttpMethod.POST, "/subsidy-applications")
								.hasAnyRole("Farmer", "ExtensionOfficer", "SubsidyAdmin", "AgriLinkAdmin")
						.requestMatchers(HttpMethod.PUT, "/subsidy-applications/**")
								.hasAnyRole("Farmer", "ExtensionOfficer", "SubsidyAdmin", "AgriLinkAdmin")
						.requestMatchers(HttpMethod.DELETE, "/subsidy-applications/**")
								.hasAnyRole("Farmer", "ExtensionOfficer", "SubsidyAdmin", "AgriLinkAdmin")
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
