package org.motoc.gamelibrary.technical.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Could not find " + id);
    }
}
