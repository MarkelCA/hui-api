package com.grupo5.huiapi.exceptions;

public class EmailTakenException extends CustomException{
    private static final String defaultMessage = "This email has already been taken";

    public EmailTakenException(String message) {
        super(message);
    }

    public EmailTakenException() {
        super(defaultMessage);
    }
}
