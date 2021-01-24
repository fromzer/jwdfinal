package com.epam.jwd.exception;

/**
 * The exception will be thrown dao layer
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class DaoException extends Exception {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
