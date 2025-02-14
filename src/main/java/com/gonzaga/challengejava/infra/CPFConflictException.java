package com.gonzaga.challengejava.infra;

public class CPFConflictException extends RuntimeException {
    public CPFConflictException(String message) {
        super(message);
    }
}
