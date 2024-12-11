package com.bridgelabz.bsa.userservice.exception;

public class UserNotFoundByIdException extends RuntimeException {

    private final String message;

    public UserNotFoundByIdException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
