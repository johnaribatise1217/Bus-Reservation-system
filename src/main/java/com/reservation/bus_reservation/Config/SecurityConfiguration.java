package com.reservation.bus_reservation.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(
        request -> request.requestMatchers("/api/auth/user/register", "/api/auth/user/login").permitAll()
        .anyRequest().authenticated()
      )
      .sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      // .exceptionHandling(Customizer.withDefaults())
      // .httpBasic(
      //   httpConfigurer -> httpConfigurer.authenticationEntryPoint(jwtAuthEntryPoint)
      // )
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
