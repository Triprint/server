package com.triprint.backend.domain.user.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.config.AppProperties;
import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final AppProperties appProperties;

	public User findById(Long userid) {
		return userRepository.findById(userid).orElseThrow(() ->
			new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND));
	}

	@Transactional
	public void quit(Long userId) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND));

		requestUnlinkKakaoAccount(user.getProviderId());
		userRepository.delete(user);
	}

	public void requestUnlinkKakaoAccount(String kakaoProviderId) throws Exception {
		URI url = new URI("https://kapi.kakao.com/v1/user/unlink");
		BodyPublisher body = BodyPublishers.ofString(
			String.format("target_id_Type=user_id&target_id=%s", kakaoProviderId));
		HttpRequest request = HttpRequest.newBuilder()
			.uri(url)
			.header("Content-Type", "application/x-www-url-encoded")
			.header("Authorization",
				String.format("KakaoAK %s", appProperties.getKakaoAccount().getServiceAppAdminKey()))
			.POST(body)
			.build();

		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		/* 동기 `HttpResponse<String>` */
		client.send(request, HttpResponse.BodyHandlers.ofString());

		/* 비동기 = `CompletableFuture<HttpResponse<String>>` */
		// client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
	}
}








