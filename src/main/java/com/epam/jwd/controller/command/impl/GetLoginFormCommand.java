package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;

/**
 * Class implements interface Command, used to get log in page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetLoginFormCommand implements Command {
    public static final ResponseContext LOGIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/loginForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return LOGIN_PAGE;
    }
}
