package com.IKov.MyChat_UserMicroservice.domain.exception;

public class BucketCreationException extends RuntimeException {
    public BucketCreationException(String message) {
        super(message);
    }
}
