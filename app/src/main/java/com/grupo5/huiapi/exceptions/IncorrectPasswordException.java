package com.grupo5.huiapi.exceptions;

public class IncorrectPasswordException extends CustomException{
    private static final String defaultMessage = "Incorrect password.";

    public IncorrectPasswordException() {
        super(defaultMessage);
    }
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
