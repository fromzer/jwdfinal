package com.epam.jwd.validation;

/**
 * Representation of violation-message
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class Violation {
    /**
     * Checked field
     */
    private String field;

    /**
     * Violation message
     */
    private String message;

    public Violation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
