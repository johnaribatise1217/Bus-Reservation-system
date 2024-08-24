package com.reservation.bus_reservation.Config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request, 
      HttpServletResponse response,
      AuthenticationException authException
    ) throws IOException, ServletException {
    response.sendError(
      HttpServletResponse.SC_UNAUTHORIZED,
      authException.getLocalizedMessage()
    );
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write("{ \"unauthorized\": \"" + authException.getMessage() + "\" }");
  }
}
