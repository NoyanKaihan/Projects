package org.example.exceptions;

public class EmptyCollectionException extends InvalidDataException {
    public EmptyCollectionException() {
        this("collection is empty");
    }

    public EmptyCollectionException(String message) {
        super(message);
    }
}
