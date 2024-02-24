package exceptions;

public class CollectionEmptyException extends RuntimeException {
    public CollectionEmptyException(String message) {
        super(message);
    }
}
