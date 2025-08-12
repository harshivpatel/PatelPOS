package com.shiv.PatelPOS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("STAFF", "MANAGER")
                                .requestMatchers(HttpMethod.POST, "/api/products").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("MANAGER")
                                .requestMatchers("/api/products/**").hasAnyRole("STAFF", "MANAGER")
                                .requestMatchers("/api/user/delete/**").hasRole("MANAGER")
                                .requestMatchers("/api/orders/**").hasAnyRole("STAFF", "MANAGER")
                                .anyRequest().authenticated()
                        )
                .addFilterBefore(jwtFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
