package com.example.contentservice.dto;

import java.time.LocalDateTime;

import com.example.contentservice.model.Genre;
import com.example.contentservice.model.VideoStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {

    private String id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String director;

    private String cast;

    private int releaseYear;

    private double movieRating;

    private String thumbnailUrl;

    private int durationInMinutes;

    // s3 key of video file
    private String videoFileKey;

    private String hlsUrl;

    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
