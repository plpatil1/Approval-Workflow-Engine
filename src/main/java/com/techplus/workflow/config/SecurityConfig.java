package com.techplus.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for APIs
                .csrf(csrf -> csrf.disable())

                // Authorization rules (ORDER MATTERS)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/requests",          // create request (no token)
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // Allow H2 console frames
                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                )

                // Disable default login form
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
}
