package com.triprint.backend.domain.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<SearchRepository, Long>, SearchRepositoryCustom {
}
