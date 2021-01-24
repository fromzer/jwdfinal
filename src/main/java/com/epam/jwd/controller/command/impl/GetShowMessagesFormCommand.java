package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to get show messages page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetShowMessagesFormCommand implements Command {
    private MessageService messageService = MessageService.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(GetShowMessagesFormCommand.class);
    public static ResponseContext SHOW_MESSAGES_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showNewMessages.jsp";
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
    @Override
    public ResponseContext execute(RequestContext requestContext) {
        try {
            requestContext.setAttribute("newMessageList", messageService.findAllNewMessage());
        } catch (ServiceException e) {
            logger.warn("Messages search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return SHOW_MESSAGES_PAGE;
    }
}
