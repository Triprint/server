package com.triprint.backend.domain.auth.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자 이름을 찾을 수 없습니다."));

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserPrincipal loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException("해당하는 id가 존재하지 않습니다."));
		return UserPrincipal.create(user);
	}
}
