package com.project.ShopApp.configurations;

import com.project.ShopApp.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer :: disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->{
                    requests
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix)
                            ).permitAll()

                            .requestMatchers(GET, String.format("%s/roles**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/categories**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/categories/**", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/categories/**", apiPrefix)).hasAnyRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/categories/**", apiPrefix)).hasAnyRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/categories/**", apiPrefix)).hasAnyRole("ADMIN")
                            .requestMatchers(GET, String.format("%s/products**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/products/**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/products/images/*", apiPrefix)).permitAll()
                            .requestMatchers(POST, String.format("%s/products**", apiPrefix)).hasAnyRole("ADMIN")
                            .requestMatchers(PUT, String.format("%s/products/**", apiPrefix)).hasAnyRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/products/**", apiPrefix)).hasAnyRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/orders/**", apiPrefix)).hasAnyRole("USER")
                            .requestMatchers(GET, String.format("%s/orders/**", apiPrefix)).permitAll()
                            .requestMatchers(PUT, String.format("%s/orders/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/orders/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST, String.format("%s/order_details/**", apiPrefix)).hasAnyRole("USER")
                            .requestMatchers(GET, String.format("%s/order_details/**", apiPrefix)).permitAll()
                            .requestMatchers(PUT, String.format("%s/order_details/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE, String.format("%s/order_details/**", apiPrefix)).hasRole("ADMIN")
                            .anyRequest().authenticated();

                });
        return http.build();
    }
}
