package com.toki.backend.filters;

import com.toki.backend.auth.entity.RefreshToekn;
import com.toki.backend.auth.repository.RefreshTokenRepository;
import com.toki.backend.utils.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Jwt 필터
 * 한번만 인증하기 위해 OnePerRequestFilter 상속(요청당 한번만 통과하면됨)
 *
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = TokenProvider.resolveToken(request);
        if(request.getRequestURI().equals("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        String requestURI =request.getRequestURI();

        Cookie[] rc = request.getCookies();
        log.debug(jwt+" : "+requestURI+  ": " +(StringUtils.hasText(jwt) && tokenProvider.validateAccessToken(jwt)));
        if (StringUtils.hasText(jwt)) {
            if(tokenProvider.validateAccessToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }else {
                String refreshTokenCookie="";
                Cookie[] cookies = request.getCookies();
                if(cookies!=null) {
                    for (Cookie cookie : cookies) {
                        if("refresh_token".equals(cookie.getName())) {
                            refreshTokenCookie=cookie.getValue();
                        }
                    }
                    if(StringUtils.hasText(refreshTokenCookie)
                            && tokenProvider.validateRefreshToken(refreshTokenCookie)
                            && tokenProvider.isFindRefreshToken(refreshTokenCookie)) {
                        RefreshToekn refreshToekn = refreshTokenRepository.findOneByUserPkAndRefreshToken(tokenProvider.parseClaims(refreshTokenCookie).get("userId", String.class),refreshTokenCookie).get();
                        response.setHeader(AUTHORIZATION_HEADER, tokenProvider.createAccessToken(refreshToekn.getUserPk(),List.of(() -> refreshToekn.getRole().toString()), refreshToekn.getSnsType()));
                    }
                }

            }
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
//            response.setHeader(AUTHORIZATION_HEADER, tokenProvider.createAccessToken("Guest",List.of(Role.GUEST::toString)));
            filterChain.doFilter(request, response);
            return;
        }
        log.debug("필터이동");
        filterChain.doFilter(request, response);
    }
}
