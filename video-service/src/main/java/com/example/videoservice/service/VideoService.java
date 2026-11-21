package com.example.videoservice.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.videoservice.config.KafkaConfig;
import com.example.videoservice.event.VideoUploadedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final S3Client s3Client;

    private final KafkaTemplate<String, VideoUploadedEvent> kafkaTemplate;

    @Value("${aws.s3.bucket-name}")
    private final String bucketName;

    private final static String KafkaTopic = "video.uploaded";

    public String uploadVideo(String movieId, MultipartFile file) throws IOException {

        String videoKey = "/raw/" + movieId + '/' + UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(videoKey)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        log.info("Video uploaded successfully" + videoKey);

        // publish event key to kafka topic

        VideoUploadedEvent videoUploadedEvent = VideoUploadedEvent.builder()
                .movieId(movieId)
                .videokey(videoKey)
                .bucketName(bucketName)
                .originalFileName(file.getOriginalFilename())
                .fileSizeInBytes(file.getSize())
                .build();

        kafkaTemplate.send(KafkaTopic, movieId, videoUploadedEvent);

        return videoKey;
    }

}
