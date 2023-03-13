package com.triprint.backend.domain.auth.security.oauth2.user;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getName() {
		@SuppressWarnings("unchecked")
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");

		if (properties == null) {
			return null;
		}

		return (String)properties.get("nickname");
	}

	@Override
	public String getEmail() {
		@SuppressWarnings("unchecked")
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

		if (kakaoAccount == null) {
			return null;
		}

		return (String)kakaoAccount.get("email");
	}

	@Override
	public String getImageUrl() {
		@SuppressWarnings("unchecked")
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");

		if (properties == null) {
			return null;
		}

		return (String)properties.get("thumbnail_image");
	}
}
