package com.FilesFlowTransHub.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers
                .httpStrictTransportSecurity(hsts -> hsts
                    .maxAgeInSeconds(31536000)  
                    .includeSubDomains(true)  
                    .preload(true)            
                )
            )
            .csrf(csrf -> csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())  
                    .ignoringRequestMatchers("/api/**")
                )
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/favicon.ico","/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/api/**").permitAll()  
                    .requestMatchers("/caseMainList/**").permitAll()  
                    .requestMatchers("/caseDetails/**").permitAll()  
                    .requestMatchers("/service/**").permitAll()  
                    .requestMatchers("/connection/**").permitAll()  
                    .requestMatchers("/callService/**").permitAll()  
                    .requestMatchers("/favicon.ico").permitAll() 
            );

        return http.build();
    }
}

