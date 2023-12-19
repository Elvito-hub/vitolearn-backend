package com.vito.vitolearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(requests -> requests
                        .requestMatchers("/community/","/community/approve","/community/request","/user/login","/articlevote/votearticle","/article/searchArticle","/articlevote/getbyarticle","/article/getbycommunity","/article/getArticleById","/user/logout","/user/getuser","/user/loggedin", "/user/register","/community/getapproved","/images/**","/user/**","/article/create").permitAll() // Exclude login and token endpoints from authentication
                        .anyRequest().authenticated());
        return http.build();
    }

}

