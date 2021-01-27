package com.epam.jwd.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Used to set default value of localization
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@WebFilter("/*")
public class LocalizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession(true);

        if (session.getAttribute("locale") == null) {
            session.setAttribute("locale", "en_US");
            Locale.setDefault(new Locale("en_US"));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
