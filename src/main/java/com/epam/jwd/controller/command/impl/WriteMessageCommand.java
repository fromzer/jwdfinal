package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.MessageService;
import com.epam.jwd.validation.MessageValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.MessageValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

public class WriteMessageCommand implements Command {
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
    public static final ResponseContext WRITE_MESSAGE_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/writeMessageForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(WriteMessageCommand.class);
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
        Locale locale = Locale.getDefault();
        String topic = requestContext.getParameter("topicForm");
        String description = requestContext.getParameter("textMessageForm");
        User user = (User) requestContext.getSessionAttribute("currentUser");
        Message message = new Message.Builder()
                .setTopic(topic)
                .setDescription(description)
                .setUserId(user.getId())
                .build();
        MessageValidator messageValidator = MessageValidatorImpl.getInstance();
        List<Violation> result = messageValidator.apply(message, locale);
        if (result.isEmpty()) {
            try {
                MessageService.getInstance().create(message);
            } catch (ServiceException e) {
                logger.warn("Message creating error");
                requestContext.setAttribute("error", e.getMessage());
                return ERROR_PAGE;
            }
        } else {
            requestContext.setAttribute("entity", message);
            requestContext.setAttribute("validateResult", result);
            return WRITE_MESSAGE_PAGE;
        }
        return MAIN_PAGE;
    }
}
