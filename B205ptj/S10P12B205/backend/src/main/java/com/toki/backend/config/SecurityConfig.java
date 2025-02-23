package com.toki.backend.config;

import com.toki.backend.auth.OAuth2LoginFailHandler;
import com.toki.backend.auth.OAuth2LoginSuccessHandler;
import com.toki.backend.auth.service.CustomOAuth2UserService;
import com.toki.backend.filters.JwtFilter;
import com.toki.backend.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;


@Configuration
@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig  {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailHandler oAuth2LoginFailHandler;
    private final TokenProvider tokenProvider;
    private final CorsConfig corsConfig;
    private static final String[] PERMIT_PATTERNS=List.of(
            "/","/css/**","/images/**","/js/**","/api/v1/auth/**","/favicon.ico"
    ).toArray(String[]::new);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 세션관리 정책을 STATELESS(세션이 있으면 쓰지도 않고, 없으면 만들지도 않는다)
                .csrf((csrf)->csrf.disable())
                .formLogin((FormLoginConfigurer::disable))
                .cors((customCorsConfig)->customCorsConfig.configurationSource(corsConfig.corsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_PATTERNS).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/room")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .oauth2Login((oauth2)->oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailHandler)
                        .userInfoEndpoint((userInfoEndpoint)->
                                userInfoEndpoint.userService(customOAuth2UserService))

                )

                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}