package com.suvey.suvey.global.security;

import com.suvey.suvey.domain.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Service
public class TokenProvider {
    private static final String SECRET_KEY="FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M1Oicegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(UserEntity userEntity){
        //토큰 만료일
        Date expiryDate = Date.from(Instant.now().plus(10, ChronoUnit.DAYS));

        return Jwts.builder()
                //header
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //payload
                .setSubject(userEntity.getId()) // userEntity.getId
                .setIssuer("suvey app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
