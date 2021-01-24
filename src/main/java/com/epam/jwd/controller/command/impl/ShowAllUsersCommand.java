package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to return all users from database
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ShowAllUsersCommand implements Command {
    public static final ResponseContext SHOW_ALL_USER = new ResponseContext() {
        @Override
        public java.lang.String getPage() {
            return "/WEB-INF/jsp/showAllUsers.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(ShowAllUsersCommand.class);
    public static ResponseContext ERROR_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/error.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private UserService userService = UserService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        try {
            requestContext.setAttribute("showAllUsers", userService.findAllUser());
        } catch (ServiceException e) {
            logger.warn("Users search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return SHOW_ALL_USER;
    }
}
