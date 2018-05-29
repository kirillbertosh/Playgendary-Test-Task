package by.playgendary.bertosh.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String exception) {
        super(exception);
    }

    public EntityNotFoundException() {

    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
