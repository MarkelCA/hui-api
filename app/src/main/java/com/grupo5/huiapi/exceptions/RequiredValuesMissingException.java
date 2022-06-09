package com.grupo5.huiapi.exceptions;

public class RequiredValuesMissingException  extends CustomException {
    private String message;
    private static final String defaultMessage = "Some required values are missing";

    public RequiredValuesMissingException(String fieldsMessage) {
        super(defaultMessage + ": (" + fieldsMessage + ")");
    }
}
