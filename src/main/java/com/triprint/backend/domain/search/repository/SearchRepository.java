package com.triprint.backend.domain.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.search.entity.Search;

public interface SearchRepository extends JpaRepository<Search, Long>, SearchRepositoryCustom {
}
