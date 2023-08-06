package com.triprint.backend.domain.trip.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ForbiddenException;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.trip.dto.CreateOrUpdateTripRequest;
import com.triprint.backend.domain.trip.dto.GetMyTripResponse;
import com.triprint.backend.domain.trip.dto.GetTripResponse;
import com.triprint.backend.domain.trip.dto.TripResponse;
import com.triprint.backend.domain.trip.entity.Trip;
import com.triprint.backend.domain.trip.repository.TripRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {

	private final UserService userService;
	private final PostRepository postRepository;
	private final TripRepository tripRepository;

	@Transactional
	public TripResponse create(Long id, CreateOrUpdateTripRequest createTripRequest) {
		User author = userService.findById(id);

		Trip trip = Trip.builder()
			.author(author)
			.title(createTripRequest.getTitle())
			.build();

		createTripRequest.getPosts().forEach((postId) -> {
			Post post = postRepository.findById(postId).orElseThrow(() -> {
				throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
			});
			validateIsAuthor(post.getAuthor().getId(), author.getId());
			trip.addPost(post);
		});
		tripRepository.save(trip);
		return new TripResponse(trip.getId());
	}

	@Transactional
	public GetTripResponse getTrip(Long id) {
		Trip trip = tripRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.TRIP_NOT_FOUND);
		});

		return new GetTripResponse(trip);
	}

	@Transactional
	public TripResponse update(Long id, Long authorId, CreateOrUpdateTripRequest updateTripRequest) {
		Trip trip = tripRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.TRIP_NOT_FOUND);
		});
		validateIsAuthor(trip.getUser().getId(), authorId);

		trip.removePosts();
		updateTripRequest.getPosts().forEach((postId) -> {
			Post post = postRepository.findById(postId).orElseThrow(() -> {
				throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
			});
			validateIsAuthor(post.getAuthor().getId(), authorId);
			trip.addPost(post);
		});
		trip.setTitle(updateTripRequest.getTitle());
		tripRepository.save(trip);
		return new TripResponse(trip.getId());
	}

	@Transactional
	public void delete(Long id, Long authorId) {
		Trip trip = tripRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.TRIP_NOT_FOUND);
		});
		validateIsAuthor(trip.getUser().getId(), authorId);
		trip.removePosts();
		tripRepository.delete(trip);
	}

	private void validateIsAuthor(Long postAuthor, Long currentUserId) {
		if (!currentUserId.equals(postAuthor)) {
			throw new ForbiddenException("작성자가 아니므로 수정할 권한이 없습니다.");
		}
	}

	public Page<GetMyTripResponse> getMyTripList(Long userId, Pageable page) {
		User user = userService.findById(userId);
		Page<Trip> tripList = tripRepository.findByTripUser(user, page);

		return tripList.map((trip) -> new GetMyTripResponse(trip));
	}
}
