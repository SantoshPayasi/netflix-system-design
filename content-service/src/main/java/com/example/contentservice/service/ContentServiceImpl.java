package com.example.contentservice.service;

import java.util.List;
import com.example.contentservice.dto.MovieRequest;
import com.example.contentservice.dto.MovieResponse;
import com.example.contentservice.model.Genre;
import com.example.contentservice.model.Movie;
import com.example.contentservice.model.VideoStatus;
import com.example.contentservice.repository.ContentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    public MovieResponse addMovie(MovieRequest movieRequest) {

        Movie movie = new Movie();
        Movie.builder()
                .title(movieRequest.getTitle())
                .description(movieRequest.getDescription())
                .genre(movieRequest.getGenre())
                .director(movieRequest.getDirector())
                .cast(movieRequest.getCast())
                .releaseYear(movieRequest.getReleaseYear())
                .movieRating(movieRequest.getMovieRating())
                .thumbnailUrl(movieRequest.getThumbnailUrl())
                .durationInMinutes(movieRequest.getDurationInMinutes())
                .videoStatus(VideoStatus.PENDING)
                .build();

        Movie savedMovie = contentRepository.save(movie);

        System.out.println("Movie saved with ID: " + savedMovie.getId());

        return mapMovieResponse(savedMovie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = contentRepository.findAll();
        return movies.stream()
                .map(this::mapMovieResponse)
                .toList();
    }

    @Override
    public MovieResponse getMovieById(String id) {
        Movie movie = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
        return mapMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getMovieByGenre(Genre genre) {
        List<Movie> movies = contentRepository.findByGenre(genre.name());
        if (movies.isEmpty()) {
            throw new RuntimeException("Movie not found with genre: " + genre);
        }

        return movies.stream()
                .map(this::mapMovieResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> searchMovies(String title) {
        List<Movie> movies = contentRepository.findByTitleContainingIgnoreCase(title);
        if (movies.isEmpty()) {
            throw new RuntimeException("Movie not found with title: " + title);
        }

        return movies.stream()
                .map(this::mapMovieResponse)
                .toList();
    }

    @Override
    public void updateVideoKey(String movieId, String videoKey) {
        Movie movie = contentRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));
        movie.setVideoFileKey(videoKey);
        movie.setVideoStatus(VideoStatus.UPLOADED);
        contentRepository.save(movie);
    }

    @Override
    public void updateHlsUrl(String movieId, String hlsUrl) {
        Movie movie = contentRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));
        movie.setHlsUrl(hlsUrl);
        movie.setVideoStatus(VideoStatus.READY);
        contentRepository.save(movie);
    }

    @Override
    public MovieResponse mapMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .releaseYear(movie.getReleaseYear())
                .movieRating(movie.getMovieRating())
                .thumbnailUrl(movie.getThumbnailUrl())
                .durationInMinutes(movie.getDurationInMinutes())
                .videoFileKey(movie.getVideoFileKey())
                .hlsUrl(movie.getHlsUrl())
                .videoStatus(movie.getVideoStatus())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }
}
