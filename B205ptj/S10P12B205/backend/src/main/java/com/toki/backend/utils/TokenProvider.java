package com.toki.backend.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.toki.backend.auth.service.CustomOAuth2User;
import com.toki.backend.auth.entity.RefreshToekn;
import com.toki.backend.auth.entity.User;
import com.toki.backend.auth.repository.RefreshTokenRepository;
import com.toki.backend.auth.repository.UserRepository;
import com.toki.backend.auth.dto.token.RefreshTokenDto;
import com.toki.backend.auth.dto.Role;
import com.toki.backend.auth.dto.token.TokenInfoDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 토큰 관련 메소드 제공 유틸, 서비스 클래스
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt-config.secret}")
    private String secretKey;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static String JwtType="Bearer ";

    private static long accessTokenExipredTime=60*30L;
    private static long refreshTokenExpiredTime=60*60*24*3L;
    private final RefreshTokenRepository tokenRepository;
    private final UserRepository userRepository;


    private Key key;

    @PostConstruct
    private void init() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        this.key= Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfoDto createToken(Authentication authentication) {
        User oAuth2User = (User) authentication.getPrincipal();


        String accessToken= createAccessToken(authentication.getName(),authentication.getAuthorities(),oAuth2User.getSnsType());

        String refreshToken= createRefreshToken(authentication.getName());

        return TokenInfoDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public static String resolveToken(HttpServletRequest request) {
        String bearerToken= request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
    public String createAccessToken(String userId, Collection<? extends GrantedAuthority> authorities, Integer snsType) {
        String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Jwts.builder()
                .subject("access_token")
                .claim("userId",userId)
                .issuedAt(Date.from(now.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .claim("hasGrade", authoritiesString)
                .claim("snsType",snsType)
                .expiration(Date.from(now.plusSeconds(accessTokenExipredTime).atZone(ZoneId.of("Asia/Seoul")).toInstant())) // set Expire Time
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(String userId) {

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Jwts.builder()
                .subject("refreshToken")
                .claim("userId",userId)
                .issuedAt(Date.from(now.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .expiration(Date.from(now.plusSeconds(refreshTokenExpiredTime).atZone(ZoneId.of("Asia/Seoul")).toInstant())) // set Expire Time
                .signWith(key)
                .compact();
    }

    public static int getRefreshTokenExpiredTime() {
        return (int) (refreshTokenExpiredTime>=Integer.MAX_VALUE?60*60*24*365:refreshTokenExpiredTime);
    }
    // 토큰에서 회원 정보 추출
    public Claims parseClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("hasGrade").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        CustomOAuth2User principal = CustomOAuth2User.builder()
                .user(User.builder()
                        .userPk(claims.get("userId").toString())
                        .userRole(Role.valueOf(claims.get("hasGrade").toString()))
                        .build()
                )
                .build();

        return new OAuth2AuthenticationToken(principal, authorities, claims.get("snsType", String.class));
    }

    public boolean validateAccessToken(String token) {
        try {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            Claims claims = parseClaims(token);
            return !claims.getExpiration().before(Date.from(now.plusSeconds(accessTokenExipredTime).atZone(ZoneId.of("Asia/Seoul")).toInstant()));
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            log.info("만료된 JWT 토큰입니다.");


        } catch (UnsupportedJwtException e) {

            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String token) {
        try {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            Claims claims = parseClaims(token);
            return !claims.getExpiration().before(Date.from(now.plusSeconds(refreshTokenExpiredTime).atZone(ZoneId.of("Asia/Seoul")).toInstant()));
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            log.info("만료된 JWT 토큰입니다.");

        } catch (UnsupportedJwtException e) {

            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public boolean isFindRefreshToken(String refreshToken) {
        Claims claims=parseClaims(refreshToken);
        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .userPk(claims.get("userId",String.class))
                .refreshToken(refreshToken)
                .build();
        int result;
        try {
            result = tokenRepository.countByRefreshToken(refreshTokenDto.getUserPk());
            return result>0;
        } catch (Exception e) {

            log.info("refreshToken finder error");
        }
        return false;

    }


    public String createAndSaveRefreshToken(User oAuth2User) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime expireTime=now.plusSeconds(refreshTokenExpiredTime);
        String refreshToekn= Jwts.builder()
                .subject("refreshToken")
                .claim("userId",oAuth2User.getUserPk())
                .issuedAt(Date.from(now.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
                .expiration(Date.from(expireTime.atZone(ZoneId.of("Asia/Seoul")).toInstant())) // set Expire Time
                .signWith(key)
                .compact();


        tokenRepository.save(RefreshToekn.builder()
                .userPk(oAuth2User.getUserPk())
                        .refreshToken(refreshToekn)
                        .snsType(oAuth2User.getSnsType())
                .role(oAuth2User.getUserRole())
                .expireTime(expireTime)
                .build()
        );
        return refreshToekn;
    }

}
