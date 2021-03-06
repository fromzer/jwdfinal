package com.epam.jwd.filter;

import com.epam.jwd.domain.impl.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Provides protection against executing commands and accessing pages from unauthenticated users
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@WebFilter("/user")
public class UserAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user == null)
            servletResponse.sendRedirect("/home?command=main");
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
