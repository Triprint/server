package com.triprint.backend.domain.auth.security.oauth2;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.auth.security.oauth2.user.AuthProvider;
import com.triprint.backend.domain.auth.security.oauth2.user.OAuth2UserInfo;
import com.triprint.backend.domain.auth.security.oauth2.user.OAuth2UserInfoFactory;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;
import com.triprint.backend.domain.user.status.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return this.process(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
		AuthProvider providerType = AuthProvider.valueOf(
			userRequest.getClientRegistration().getRegistrationId().toUpperCase());

		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
		User savedUser = userRepository.findByProviderIdAndProvider(userInfo.getId(), providerType)
			.orElseGet(() -> createUser(userInfo, providerType));
		return UserPrincipal.create(savedUser, user.getAttributes());
	}

	private User createUser(OAuth2UserInfo userInfo, AuthProvider providerType) {
		User user = User.builder()
			.providerId(userInfo.getId())
			.profileImg(userInfo.getImageUrl())
			.username(userInfo.getName())
			.email(userInfo.getEmail())
			.provider(providerType)
			.userRole(UserRole.USER)
			.build();

		return userRepository.save(user);
	}
}
