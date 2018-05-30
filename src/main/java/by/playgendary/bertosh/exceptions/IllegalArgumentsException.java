package by.playgendary.bertosh.exceptions;

public class IllegalArgumentsException extends RuntimeException {

    public IllegalArgumentsException(String message) {
        super(message);
    }

    public IllegalArgumentsException() {
        super();
    }
}
