package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

public class ShowUserRequestsCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowUserRequestsCommand.class);
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
    public static ResponseContext USER_REQUESTS_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showUserRequests.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private UserSectionService userSectionService = UserSectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        HttpSession session = requestContext.getSession();
        User user = (User) session.getAttribute("currentUser");
        try {
            requestContext.setAttribute("showAllUserRequests", userSectionService.findAllUserRequests(user.getId()));
        } catch (ServiceException e) {
            logger.warn("Show user request error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return USER_REQUESTS_PAGE;
    }
}
