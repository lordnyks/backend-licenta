package com.monitoring.documents.exceptions;

import java.io.Serializable;

public class TokenExpiredException extends RuntimeException implements Serializable {

    public TokenExpiredException(String message) {
        super(message);
    }
}
