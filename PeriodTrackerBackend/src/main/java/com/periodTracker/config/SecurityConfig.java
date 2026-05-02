package com.periodTracker.config;

import com.periodTracker.utility.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Autowired
   private JwtFilter jwtFilter;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       return http.cors(Customizer.withDefaults())
               .csrf(csrf-> csrf.disable())
               .authorizeHttpRequests(auth -> auth

                       // Public APIs
                       .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                       .requestMatchers("/api/v1/api/user/**").permitAll()

                       // Secure everything else
                       .anyRequest().authenticated()
               )
               .sessionManagement(session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
               .httpBasic(httpBasic -> httpBasic.disable())
               .formLogin(formLogin -> formLogin.disable())
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
   }
   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration corsConfiguration = new CorsConfiguration();
       corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
       corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
       corsConfiguration.setAllowedHeaders(List.of("Authorization","Content-Type","Accept","Origin"));
       corsConfiguration.setExposedHeaders(List.of("Authorization"));
       corsConfiguration.setAllowCredentials(true);
       UrlBasedCorsConfigurationSource source =
               new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", corsConfiguration);
       return source;
   }
}
