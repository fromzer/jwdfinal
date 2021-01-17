package com.epam.jwd.controller.command;

import javax.servlet.http.HttpSession;

public interface RequestContext {

    void setAttribute(String name, Object attr);

    String getParameter(String name);

    HttpSession getSession(boolean create);

    HttpSession getSession();

    Object getSessionAttribute(String name);

    String[] getParameterValues(String date);
}
