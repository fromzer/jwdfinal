package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.MessageService;
import com.epam.jwd.service.impl.UserService;
import com.epam.jwd.util.EmailSender;
import com.epam.jwd.validation.MessageValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.MessageValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

/**
 * Class implements interface Command, used by administrator to send message
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class SendMessageCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SendMessageCommand.class);
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
    public static ResponseContext SHOW_NEW_MESSAGES = new ResponseContext() {
        @Override
        public String getPage() {
            return "/admin?command=messages";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private UserService userService = UserService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Locale locale = Locale.getDefault();
        Long userMessageId = Long.valueOf(requestContext.getParameter("messageId"));
        Long userId = Long.valueOf(requestContext.getParameter("userId"));
        String topic = requestContext.getParameter("topicForm");
        String description = requestContext.getParameter("textMessageForm");
        Message message = new Message.Builder()
                .setTopic(topic)
                .setDescription(description)
                .build();
        MessageValidator messageValidator = MessageValidatorImpl.getInstance();
        List<Violation> result = messageValidator.apply(message, locale);
        return getPage(requestContext, userMessageId, userId, topic, description, message, result);
    }

    private ResponseContext getPage(RequestContext requestContext, Long userMessageId, Long userId, String topic, String description, Message message, List<Violation> result) {
        try {
            User user = userService.findUserById(userId);
            if (result.isEmpty()) {
                send(userMessageId, user.getEmail(), topic, description);
            } else {
                requestContext.setAttribute("messageId", userMessageId);
                requestContext.setAttribute("user", user);
                requestContext.setAttribute("entity", message);
                requestContext.setAttribute("validateResult", result);
                return SHOW_MESSAGE_REPLY_PAGE;
            }
        } catch (ServiceException e) {
            logger.warn("Send message error");
            requestContext.setAttribute("error", e);
            return ERROR_PAGE;
        }
        return SHOW_NEW_MESSAGES;
    }

    private void send(Long userMessageId, String email, String topic, String description) throws ServiceException {
        EmailSender.send(topic, description, email);
        Message userMessage = new Message.Builder()
                .setId(userMessageId)
                .setAnswered(true)
                .build();
        MessageService.getInstance().update(userMessage);
    }
}
