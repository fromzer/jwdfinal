package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetMessageReplyFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GetMessageReplyFormCommand.class);
    public static ResponseContext SHOW_MESSAGE_REPLY_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/messageReplyForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
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
    private UserService userService = UserService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        try {
            Long userMessageId = Long.valueOf(requestContext.getParameter("messageId"));
            Long userId = Long.parseLong(requestContext.getParameter("userId"));
            requestContext.setAttribute("user", userService.findUserById(userId));
            requestContext.setAttribute("messageId", userMessageId);
        } catch (ServiceException e) {
            logger.warn("Email search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return SHOW_MESSAGE_REPLY_PAGE;
    }
}
