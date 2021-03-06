package com.epam.jwd.filter;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Provides protection against executing admin commands and accessing admin pages
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@WebFilter("/admin")
public class AdminAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user != null && user.getRole().equals(UserRole.ADMIN))
            chain.doFilter(request, response);
        else {
            servletResponse.sendRedirect("/home?command=main");
        }
    }

    @Override
    public void destroy() {
    }
}
