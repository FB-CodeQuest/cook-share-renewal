package com.fbcq.backend.auth.infrastructure.jwt;

import com.fbcq.backend.auth.domain.CustomUserDetails;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}") // access token: ms 단위
    private long expiration;

    @Value("${jwt.refresh-expiration}") // refresh token: ms 단위
    private long refreshExpiration;

    /**
     * AccessToken 생성
     * - subject: userId
     * - role: 사용자 권한 정보 claim에 포함
     */
    public String createToken(Integer userId, String role) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("role", role);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * RefreshToken 생성
     * - subject는 구분용 "refresh"
     * - userId는 별도 claim에 저장
     */
    public String createRefreshToken(Integer userId) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject("refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpiration))
                .claim("type", "refresh")
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 토큰 유효성 검증
     * - 서명 무결성 및 만료 여부 확인
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 토큰에서 Authentication 객체 생성
     * - SecurityContext에 주입할 CustomUserDetails 구성
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Integer userId = Integer.valueOf(claims.getSubject());
        String role = claims.get("role", String.class);

        CustomUserDetails userDetails = new CustomUserDetails(userId, role);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 요청 헤더에서 Bearer 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    /**
     * accessToken에서 사용자 ID 추출
     * - 주로 유저 ID 기반 요청 처리가 필요할 때 사용
     * - ex: 특정 API에서 명시적 인증 없이 토큰에서 유저 ID만 뽑고 싶을 때
     */
    public Long getUserIdFromAccessToken(String token) {
        Claims claims = parseClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    /**
     * refreshToken에서 사용자 ID 추출
     * - userId를 별도 claim으로 저장해두었기 때문에 get("userId")
     */
    public Integer getUserIdFromRefreshToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("userId", Integer.class);
    }

    /**
     * 공통 claims 파싱 처리
     * - 만료된 토큰이어도 claims는 꺼낼 수 있도록 처리
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
