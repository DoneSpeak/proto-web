package io.github.donespeak.protoservice.core.exception;

/**
 * @author Yang Guanrong
 */
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {}

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
