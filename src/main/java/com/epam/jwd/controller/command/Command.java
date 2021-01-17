package com.epam.jwd.controller.command;

public interface Command {
    ResponseContext execute(RequestContext requestContext);
}
