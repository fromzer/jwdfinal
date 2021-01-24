package com.epam.jwd.controller.command;

/**
 * The interface returns the page and the transition method
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface ResponseContext {

    /**
     * Get page to go
     *
     * @return string with address or command
     */
    String getPage();

    /**
     * Transition rule, forward or redirect
     *
     * @return true if redirect, false if forward
     */
    boolean isRedirect();
}
