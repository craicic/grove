package org.motoc.gamelibrary.technical.exception;

public class NotFoundException extends RuntimeException {

    @Deprecated
    public NotFoundException(Long id) {
        super("Could not find " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
