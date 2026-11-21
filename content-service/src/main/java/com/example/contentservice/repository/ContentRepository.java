package com.example.contentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.contentservice.model.Movie;

public interface ContentRepository extends JpaRepository<Movie, String> {
    List<Movie> findByGenre(String genre);

    List<Movie> findByTitleContainingIgnoreCase(String title);
}
