package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;

import javax.servlet.http.HttpSession;

/**
 * Class implements interface Command, used to log out
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class LogoutCommand implements Command {
    public static final ResponseContext MAIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=main";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        HttpSession session = requestContext.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return MAIN_PAGE;
    }
}
