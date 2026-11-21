package com.example.contentservice.service;

import java.util.List;
import com.example.contentservice.dto.MovieRequest;
import com.example.contentservice.dto.MovieResponse;
import com.example.contentservice.model.Genre;
import com.example.contentservice.model.Movie;

public interface ContentService {
    MovieResponse addMovie(MovieRequest movieRequest);

    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(String id);

    List<MovieResponse> getMovieByGenre(Genre genre);

    MovieResponse mapMovieResponse(Movie movie);

    List<MovieResponse> searchMovies(String title);

    void updateVideoKey(String movieId, String videoKey);

    void updateHlsUrl(String movieId, String hlsUrl);
}
