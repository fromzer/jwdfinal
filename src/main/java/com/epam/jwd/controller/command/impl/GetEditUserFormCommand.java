package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to get edit user page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetEditUserFormCommand implements Command {
    public static final ResponseContext EDIT_USER_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/editUserForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(GetEditUserFormCommand.class);
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
        Long id = Long.valueOf(requestContext.getParameter("userId"));
        try {
            requestContext.setAttribute("userData", service.findUserById(id));
        } catch (ServiceException e) {
            logger.warn("User search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_USER_PAGE;
    }
}
