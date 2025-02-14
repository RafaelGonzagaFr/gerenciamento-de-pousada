package com.gonzaga.challengejava.infra;

public class RoomNumberAlreadyExistsException extends RuntimeException {
    public RoomNumberAlreadyExistsException(String message) {
        super(message);
    }
}
