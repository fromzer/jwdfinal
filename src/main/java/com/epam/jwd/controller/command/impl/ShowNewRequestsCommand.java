package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

public class ShowNewRequestsCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowNewRequestsCommand.class);
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
            return "/WEB-INF/jsp/showNewUserRequests.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private UserSectionService userSectionService = UserSectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        try {
            requestContext.setAttribute("showNewRequests", userSectionService.findNewRequests());
        } catch (ServiceException e) {
            logger.warn("Show new request error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return USER_REQUESTS_PAGE;
    }
}
