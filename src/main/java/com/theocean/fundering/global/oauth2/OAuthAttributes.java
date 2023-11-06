package com.theocean.fundering.global.oauth2;

import com.theocean.fundering.domain.member.domain.Member;
import com.theocean.fundering.domain.member.domain.constant.UserRole;
import com.theocean.fundering.global.oauth2.userInfo.KakaoOAuth2UserInfo;
import com.theocean.fundering.global.oauth2.userInfo.OAuth2UserInfo;
import com.theocean.fundering.global.utils.PasswordUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final String nameAttributeKey;
    private final OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(final String nameAttributeKey, final OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(final String userNameAttributeName, final Map<String, Object> attributes) {
        return builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .userId(oauth2UserInfo.getId())
                .nickname(oauth2UserInfo.getNickname())
                .email(oauth2UserInfo.getEmail())
                .password(PasswordUtil.generateRandomPassword())//무작위 비밀번호 암호화해서 생성
                .profileImage(oauth2UserInfo.getImageUrl())
                .userRole(UserRole.USER)
                .build();
    }
}