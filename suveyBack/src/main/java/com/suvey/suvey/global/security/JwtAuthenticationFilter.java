package com.suvey.suvey.global.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
/*
 *
 * JwtFilter는 클라이언트의 요청이 서버의 리소스에 도달하기 전에 JWT 토큰의 유효성을 검사하고,
 * 해당 토큰을 기반으로 사용자를 인증하는 역할을 함.
 *
 * https://velog.io/@qowl880/Spring-Security-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-ContextHolder
 * */

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {


            String token = parseBearerToken(request);

            log.info("jwt filter is running...");

            if (token != null && !token.equalsIgnoreCase("null")) {
                String userId = tokenProvider.validateAndGetUser(token);

                log.info("Authenticated user ID : " + userId);

                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, //Todo <- AuthenticationPrincipal(또는 principal)
                        null,
                        AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);

            }
        }
        catch (ExpiredJwtException e){
            logger.error("Expired JWT token");
            throw new JwtException("Expired JWT token");
            //Todo 토큰 만기시 로직 처리
        }
        catch (Exception e){
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        //프론트에서 아래 문구로 시작하는 헤더를 포함하는 토큰을 쏴줘야한다.
        //Authorization : Bearer + accesstoken 이렇게 되어 있을 것이다
        // accesstoken 은 로그인 할때 생성되어 브라우저 로컬 스토리지에 저장되어 있다.
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
