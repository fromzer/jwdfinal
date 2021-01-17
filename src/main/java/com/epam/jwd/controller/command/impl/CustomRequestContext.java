package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomRequestContext implements RequestContext {
    private final HttpServletRequest httpServletRequest;

    public CustomRequestContext(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void setAttribute(String name, Object attr) {
        httpServletRequest.setAttribute(name, attr);
    }

    @Override
    public String getParameter(String name) {
        return httpServletRequest.getParameter(name);
    }

    @Override
    public HttpSession getSession(boolean create) {
        return httpServletRequest.getSession(create);
    }

    @Override
    public HttpSession getSession() {
        return httpServletRequest.getSession();
    }

    @Override
    public Object getSessionAttribute(String name) {
        return httpServletRequest.getSession().getAttribute(name);
    }

    @Override
    public String[] getParameterValues(String date) {
        return httpServletRequest.getParameterValues(date);
    }
}
