package com.farm2pot.security.service;

import com.farm2pot.common.exception.BaseException;
import com.farm2pot.utils.exception.UserErrorCode;
import com.farm2pot.security.config.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * packageName    : com.farm2pot.auth.service
 * author         : TAEJIN
 * date           : 2025-10-03
 * description    :
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final SecurityProperties securityProperties;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Access Token 발급
     */
    public String generateAccessToken(Long id, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityProperties.getRefreshExpiration());

        return Jwts.builder()
                .subject(id.toString())
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Refresh Token 발급
     */
    public String generateRefreshToken(Long id) {
        Date now = new Date();
        Date refreshExpiry = new Date(now.getTime() + securityProperties.getRefreshExpiration());

        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(now)
                .expiration(refreshExpiry)
                .signWith(key)
                .compact();
    }

    /**
     * 토큰 만료 시간 계산
     */
    public long getExpiration(String token) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.getTime() - System.currentTimeMillis();
        } catch (ExpiredJwtException e) {
            throw new BaseException(UserErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new BaseException(UserErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 사용자 아이디(PK) 추출
     */
    public String getId(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new BaseException(UserErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new BaseException(UserErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BaseException(UserErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException | SignatureException e) {
            throw new BaseException(UserErrorCode.INVALID_TOKEN);
        } catch (JwtException e) {
            throw new BaseException(UserErrorCode.UNAUTHORIZED_USER);
        } catch (IllegalArgumentException e) {
            throw new BaseException(UserErrorCode.NULL_TOKEN);
        }
    }

    /**
     * Roles 추출
     */
    public List<String> getRoles(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Object rolesObj = claims.get("roles");
            if (rolesObj instanceof List<?> rolesList) {
                return rolesList.stream().map(Object::toString).toList();
            }
            if (rolesObj instanceof String rolesStr) {
                return Arrays.asList(rolesStr.split(","));
            }
            return Collections.emptyList();

        } catch (ExpiredJwtException e) {
            throw new BaseException(UserErrorCode.EXPIRED_TOKEN);
        } catch (JwtException e) {
            throw new BaseException(UserErrorCode.INVALID_TOKEN);
        }
    }
}