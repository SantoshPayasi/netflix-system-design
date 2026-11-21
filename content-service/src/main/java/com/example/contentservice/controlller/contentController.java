package com.example.contentservice.controlller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.contentservice.dto.*;

import com.example.contentservice.service.ContentServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.contentservice.model.Genre;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class contentController {

    private final ContentServiceImpl contentService;

    // add movie to catelog
    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@RequestBody @Valid MovieRequest movieRequest) {
        // MovieResponse movieResponse = contentService.addMovie(movieRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.addMovie(movieRequest));

    }

    @GetMapping
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(contentService.getAllMovies());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(contentService.getMovieById(id));
    }

    @PutMapping("/genre/{genre}")
    public ResponseEntity<List<MovieResponse>> updateMovie(@PathVariable Genre genre) {

        return ResponseEntity.status(HttpStatus.OK).body(contentService.getMovieByGenre(genre));
    }

}
