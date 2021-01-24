package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to delete user
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class DeleteUserCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteUserCommand.class);
    public static ResponseContext USER_LIST_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=showAllUsers";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
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
    private UserService service = UserService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long userId = Long.valueOf(requestContext.getParameter("userId"));
        User user = new User();
        user.setId(userId);
        try {
            service.delete(user);
        } catch (ServiceException e) {
            logger.warn("User deleting error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return USER_LIST_PAGE;
    }
}
