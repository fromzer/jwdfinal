package com.epam.jwd.exception;

/**
 * The exception will be thrown service layer
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
