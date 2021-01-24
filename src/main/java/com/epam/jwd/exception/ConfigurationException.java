package com.epam.jwd.exception;

/**
 * The exception will be thrown with incorrect connection pool initialization
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ConfigurationException extends RuntimeException{

    public ConfigurationException(String message) {
        super(message);
    }
}
