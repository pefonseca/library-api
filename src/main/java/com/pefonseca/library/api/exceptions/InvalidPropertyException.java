package com.pefonseca.library.api.exceptions;

import lombok.Getter;

public class InvalidPropertyException extends RuntimeException {

    @Getter
    private String property;

    public InvalidPropertyException(String property, String message) {
        super(message);
        this.property = property;
    }

}
