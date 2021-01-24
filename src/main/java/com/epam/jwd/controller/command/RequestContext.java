package com.epam.jwd.controller.command;

import javax.servlet.http.HttpSession;

/**
 * Interface encapsulate data from clients HttpRequest
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface RequestContext {

    /**
     * Stores an attribute in request.
     *
     * @param name a String specifying the name of the attribute
     * @param attr the Object to be stored
     */
    void setAttribute(String name, Object attr);

    /**
     * Returns an first String value of the given request parameter
     *
     * @param name a string name of parameter
     * @return parameter's value
     */
    String getParameter(String name);

    /**
     * Returns the current session associated with this request
     *
     * @param create true to create a new session for this request if necessary;
     *               false to return null if there's no current session
     * @return the HttpSession associated with this request
     */
    HttpSession getSession(boolean create);

    /**
     * Returns the current session associated with this request
     *
     * @return the HttpSession associated with this request
     */
    HttpSession getSession();

    /**
     * Returns the object bound with the specified name in this session
     *
     * @param name a string specifying the name of the object
     * @return the object with the specified name
     */
    Object getSessionAttribute(String name);

    /**
     * Returns an array of String objects containing all of the values the given request parameter has
     *
     * @param data a String containing the name of the parameter whose value is requested
     * @return an array of String objects containing the parameter's values
     */
    String[] getParameterValues(String data);
}
