package com.theocean.fundering.global.oauth2.service;

import com.theocean.fundering.domain.member.domain.Member;
import com.theocean.fundering.domain.member.repository.MemberRepository;
import com.theocean.fundering.global.oauth2.CustomOAuth2User;
import com.theocean.fundering.global.oauth2.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser 실행");

        final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        final OAuth2User oAuth2User = delegate.loadUser(userRequest);

        final String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        final Map<String, Object> attributes = oAuth2User.getAttributes();

        final OAuthAttributes extractedAttributes = OAuthAttributes.of(userNameAttributeName, attributes);

        final Member createdUser = getUser(extractedAttributes);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getUserRole().name())),
                attributes,
                extractedAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getUserRole()
        );
    }

    private Member getUser(final OAuthAttributes attributes) {
        final Member findMember = memberRepository.findById(attributes.getOauth2UserInfo().getId()).orElse(null);

        if (null == findMember) {
            return saveMember(attributes);
        }
        return findMember;
    }

    private Member saveMember(final OAuthAttributes attributes) {
        final Member newMember = attributes.toEntity();
        return memberRepository.save(newMember);
    }
}