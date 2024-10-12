package com.security.securityDemoProject.config;

import com.security.securityDemoProject.util.Constant;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("roles", roles) // Lưu danh sách vai trò vào token
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public List<String> getRolesFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("roles");
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Constant.ErrorStatus validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return Constant.ErrorStatus.SUCCESS;
        } catch (SignatureException e) {
            System.out.println(e.getMessage());
            return Constant.ErrorStatus.INVALID_SIGNATURE;
        } catch (MalformedJwtException e) {
            System.out.println(e.getMessage());
            return Constant.ErrorStatus.MALFORMED;
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
            return Constant.ErrorStatus.EXPIRED;
        } catch (UnsupportedJwtException e) {
            System.out.println(e.getMessage());
            return Constant.ErrorStatus.UNSUPPORTED;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Constant.ErrorStatus.ILLEGAL_ARGUMENT;
        }
    }
}