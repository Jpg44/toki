package com.toki.backend.auth.service;

import com.toki.backend.auth.entity.User;
import com.toki.backend.auth.repository.UserRepository;
import com.toki.backend.auth.dto.Role;
import com.toki.backend.auth.dto.snsUser.KakaoUserInfo;
import com.toki.backend.auth.dto.snsUser.NaverUserInfo;
import com.toki.backend.auth.dto.snsUser.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo =switch (userRequest.getClientRegistration().getRegistrationId()){
            case "naver" -> new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
            case "kakao" -> new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"),
                    String.valueOf(oAuth2User.getAttributes().get("id")));
            default -> null;
        };
        String userId = oAuth2UserInfo.getProviderId();
        String userEmail= oAuth2UserInfo.getEmail();
        String userName = oAuth2UserInfo.getName();
        String userBirthYear= oAuth2UserInfo.getBirthYear();
        String userNickname = oAuth2UserInfo.getNickname();
        String userProfileImageUrl= oAuth2UserInfo.getProfileImageUrl();
        int snsType= switch (oAuth2UserInfo.getProvider()){
            case "naver" -> 2;
            case "kakao" -> 3;
            default -> 1;
        };
        log.debug("providerId {}, Objet : {}",userId, oAuth2UserInfo.toString());
        User user =userRepository.findByUserIdAndSnsType(userId,snsType).orElse(
                null
        );
        if(user==null){
            user=User.builder()
                    .userId(userId)
                    .userEmail(userEmail)
                    .userName(userName)
                    .snsType(snsType)
                    .userRole(Role.USER)
                    .userNickName(userNickname)
                    .birthYear(userBirthYear)
                    .profileImageUrl(userProfileImageUrl)
                    .build();
            userRepository.save(user);
        }
        return new CustomOAuth2User(user,oAuth2User.getAttributes());
    }

    /* ... */

}