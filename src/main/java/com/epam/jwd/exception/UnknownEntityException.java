package com.epam.jwd.exception;

/**
 * The exception will be thrown when unknown enum id is used
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class UnknownEntityException extends Exception{

    public UnknownEntityException(String message) {
        super(message);
    }
}
