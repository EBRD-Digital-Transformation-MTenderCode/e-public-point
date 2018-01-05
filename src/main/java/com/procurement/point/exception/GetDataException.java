package com.procurement.point.exception;

import lombok.Getter;

@Getter
public class GetDataException extends RuntimeException {

    private final String message;

    public GetDataException(final String message) {
        this.message = message;
    }
}
