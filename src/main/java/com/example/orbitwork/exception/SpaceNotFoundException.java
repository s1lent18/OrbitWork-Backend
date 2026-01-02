package com.example.orbitwork.exception;

public class SpaceNotFoundException extends RuntimeException {
    public SpaceNotFoundException(String message) {
        super(message);
    }
}
