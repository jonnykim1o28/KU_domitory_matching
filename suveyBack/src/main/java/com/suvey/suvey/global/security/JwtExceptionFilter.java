package com.suvey.suvey.global.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (JwtException ex){
            setErrorResponse(HttpStatus.UNAUTHORIZED,response,ex);
        }
    }
    //Todo 만료된 토큰이 브라우저에 남아 있을 때 이를 처리해야함
    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException{
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

    }

}
