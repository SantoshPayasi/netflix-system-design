package com.example.contentservice.model;

/**
 * VideoStatus
 * PENDING->UPLOADED->ENCODING-> ENCODED ->READY
 * PENDING->UPLOADED->ENCODING->FAILED
 */
public enum VideoStatus {
    PENDING,  //movie added but not uploaded yet
    UPLOADED, // raw video uploaded to s3 but not encoded yet
    ENCODING, // raw video is being encoded to hls format
    ENCODED,  // raw video is encoded to hls format but not ready for streaming yet
    READY,    // hls video is ready for streaming
    FAILED     // encoding failed
}
