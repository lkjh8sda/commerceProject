package com.zerobase.commerceproject.config;

import com.zerobase.commerceproject.domain.UserType;
import com.zerobase.commerceproject.domain.UserVo;
import com.zerobase.commerceproject.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtAuthenticationProvider {
    private String secretKey = "secretKey";

    private long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String usePK, Long id, UserType userType){
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(usePK)).setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles",userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            //만료일이 현재시간보다 작을 때만
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public UserVo getUserVo(String token){
        Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Aes256Util.decrypt(c.getId())), Aes256Util.decrypt(c.getSubject()));
    }
}
