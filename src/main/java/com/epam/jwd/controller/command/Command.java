package com.epam.jwd.controller.command;

/**
 * Base interface for command that is responsible for handling client request
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface Command {

    /**
     * The method performs actions depending on the passed parameters
     * encapsulated in requestContext object
     *
     * @param requestContext contains data for handling user request
     * @return ResponseContext object as result of executing command
     */
    ResponseContext execute(RequestContext requestContext);
}
