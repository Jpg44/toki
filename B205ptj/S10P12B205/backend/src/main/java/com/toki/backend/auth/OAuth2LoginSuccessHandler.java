package com.toki.backend.auth;

import com.toki.backend.auth.service.CustomOAuth2User;
import com.toki.backend.auth.entity.User;
import com.toki.backend.utils.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;

/**
 * Oauth2 로그인 성공시 처리 핸들러 사용자 정의 구현
 * mudokja@gmail.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    @Value("${toki.baseurl.frontend}")
    private String frontendBaseurl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");
        try {
            User oAuth2User = ((CustomOAuth2User) authentication.getPrincipal()).toEntity();
            System.out.println(oAuth2User.toString());
            log.debug(oAuth2User.toString());
            loginSuccess(response, oAuth2User,authentication.getAuthorities()); // 로그인에 성공한 경우 access, refresh 토큰 생성
        } catch (Exception e) {

            log.debug("로그인 에러: {}",e.getMessage());
        }
        log.info("로그인 절차 완료!");


    }

    private void loginSuccess(HttpServletResponse response, User oAuth2User, Collection<? extends GrantedAuthority> authorities) throws IOException {
        String accessToken = tokenProvider.createAccessToken(oAuth2User.getUserPk(), authorities, oAuth2User.getSnsType());
        String refreshToken = tokenProvider.createAndSaveRefreshToken(oAuth2User);
        response.addHeader(TokenProvider.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(TokenProvider.getRefreshTokenExpiredTime());
        cookie.setPath("/");
        cookie.setDomain("localhost");
//        cookie.setSecure(true); // https가 아니므로 아직 안됨
        response.addCookie(cookie);
        Cookie deleteJSI=new Cookie("JSESSIONID","");
        deleteJSI.setMaxAge(1);
        deleteJSI.setHttpOnly(true);
        response.addCookie(deleteJSI);
        log.debug("토큰 :{}",accessToken);
        UriComponents uriComponent= UriComponentsBuilder.fromHttpUrl(frontendBaseurl)
                .pathSegment("auth","login")
                .queryParam("resultCode",200)
                .queryParam("token",accessToken)
                .encode()
                .build();
        response.sendRedirect(uriComponent.toString());

    }
}