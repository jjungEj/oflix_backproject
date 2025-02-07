package com.oflix.OFlix_back.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //CORS
        http.cors(cors -> cors.configurationSource(request -> {
            var config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:5173"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
//            config.setAllowCredentials(true);
//            config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
//            config.setExposedHeaders(List.of("Authorization"));
            return config;
        }));

        //접근 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/cinemas").permitAll());
        return http.build();
    }

}
