package com.ra.security.jwt;

import com.ra.security.principle.MyUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${expired}")
    private Long EXPRIED;

    @Value("${secret-key}")
    private String SECRET;

    private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(MyUserDetails myUserDetails) {
//        Tao thoi gian song cua Token
        Date dateExoiration = new Date(new Date().getTime() + EXPRIED);
        return Jwts.builder().setSubject(myUserDetails.getUsername())
                .signWith(SignatureAlgorithm.HS512, SECRET).setExpiration(dateExoiration)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpressionException | SignatureException |
                 MalformedJwtException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

}
