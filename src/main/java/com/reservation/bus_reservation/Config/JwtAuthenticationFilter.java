package com.reservation.bus_reservation.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final UserDetailsService userDetailsService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request, 
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  )
    throws ServletException, IOException 
  {
    final String authHeader = request.getHeader("Authorization");
    final String Jwt;
    final String userEmail;

    //check if the authHeader is valid
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      filterChain.doFilter(request, response);
      return ;
    }

    try {
      //if it is valid extract the jwtToken from the authHeader
      Jwt = authHeader.substring(7);
      //extract the user email(username) from the Jwt token
      userEmail = jwtService.extractUsername(Jwt);

      //check the authentication status of the user, (check if the userEmail is not empty/null and if that email has 
      ///been previously authenticated)
      if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        if(jwtService.isTokenValid(userDetails, Jwt)){
          //update securityContext
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
          );

          authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
    }
  }

}
