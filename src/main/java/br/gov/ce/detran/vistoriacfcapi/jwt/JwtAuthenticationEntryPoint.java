package br.gov.ce.detran.vistoriacfcapi.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
            //log.info("Http Status 401 {}", authException.getMessage());
            response.setHeader("www-authenticate", "Bearer realm='/api/v1/auth'");
            response.sendError(401);
    }
    
}
