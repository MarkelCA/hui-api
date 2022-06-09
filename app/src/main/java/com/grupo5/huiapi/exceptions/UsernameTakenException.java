package com.grupo5.huiapi.exceptions;

public class UsernameTakenException extends CustomException {
        private static final String defaultMessage = "This username has already been taken";

        public UsernameTakenException(String message) { super(message); }
        public UsernameTakenException() {
            super(defaultMessage);
        }

}
