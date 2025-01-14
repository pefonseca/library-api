package com.pefonseca.library.api.exceptions;

public class OperationNotPermitted extends RuntimeException {

    public OperationNotPermitted(String message) {
        super(message);
    }

}
