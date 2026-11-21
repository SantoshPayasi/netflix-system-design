package com.example.videoservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kafka Event when video is uploaded in s3 .
 * It contains information about the uploaded video, such as its key and the
 * associated movie ID.
 * 
 * TOPIC: video.uploaded
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoUploadedEvent {
    private String movieId;

    private String videokey;

    private String bucketName;

    private String originalFileName;

    private long fileSizeInBytes;
}
