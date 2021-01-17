package com.epam.jwd.filter;

import com.epam.jwd.domain.impl.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user")
public class UserAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        User user = (User) session.getAttribute("currentUser");
        boolean isLoggedIn = (user != null);
        if (!isLoggedIn)
            servletResponse.sendRedirect("/home?command=errorAuth");
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
