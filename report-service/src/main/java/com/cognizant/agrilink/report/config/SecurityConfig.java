package com.cognizant.agrilink.report.config;

import com.cognizant.agrilink.report.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // audit-logs
                .requestMatchers(HttpMethod.GET, "/audit-logs", "/audit-logs/**")
                    .hasAnyRole("ComplianceAnalyst", "AgriLinkAdmin")
                // agri-reports (Farmer NO access)
                .requestMatchers(HttpMethod.GET, "/agri-reports", "/agri-reports/**")
                    .hasAnyRole("ExtensionOfficer", "ProcurementOfficer", "SubsidyAdmin",
                                "ComplianceAnalyst", "AgriLinkAdmin")
                .requestMatchers(HttpMethod.POST, "/agri-reports")
                    .hasAnyRole("SubsidyAdmin", "ComplianceAnalyst", "AgriLinkAdmin")
                .requestMatchers(HttpMethod.PUT, "/agri-reports/**")
                    .hasAnyRole("SubsidyAdmin", "ComplianceAnalyst", "AgriLinkAdmin")
                .requestMatchers(HttpMethod.DELETE, "/agri-reports/**")
                    .hasAnyRole("SubsidyAdmin", "ComplianceAnalyst", "AgriLinkAdmin")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
