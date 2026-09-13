package com.jung.daysum.oauth;

import com.jung.daysum.domain.User;
import com.jung.daysum.domain.enums.Role;
import com.jung.daysum.domain.enums.SocialType;
import com.jung.daysum.oauth.userinfo.KakaoOAuth2UserInfo;
import com.jung.daysum.oauth.userinfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return User.UserSaveBuilder()
                .email(UUID.randomUUID() + "@socialUser.com")
                .role(Role.ROLE_GUEST)
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .nickname(oauth2UserInfo.getNickname())
                .build();
    }
}
