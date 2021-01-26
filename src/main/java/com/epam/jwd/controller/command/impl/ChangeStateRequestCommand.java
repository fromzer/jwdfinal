package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.State;
import com.epam.jwd.domain.impl.UserSection;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.exception.UnknownEntityException;
import com.epam.jwd.service.impl.UserSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to change user request state
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ChangeStateRequestCommand implements Command {
    public static final ResponseContext ERROR_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/error.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(ChangeStateRequestCommand.class);
    public static ResponseContext NEW_REQUEST_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/admin?command=showNewRequests";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    private UserSectionService userSectionService = UserSectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long userSectionId = Long.valueOf(requestContext.getParameter("userSectionId"));
        Long sectionId = Long.valueOf(requestContext.getParameter("sectionId"));
        Long userId = Long.valueOf(requestContext.getParameter("userId"));
        Long stateId = Long.valueOf(requestContext.getParameter("states"));
        State state;
        try {
            state = State.resolveStateById(stateId);
            UserSection userSection = new UserSection.Builder()
                    .setId(userSectionId)
                    .setSectionId(sectionId)
                    .setUserId(userId)
                    .setState(state)
                    .build();
            userSectionService.update(userSection);
        } catch (ServiceException | UnknownEntityException e) {
            logger.warn("UserSection creation error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return NEW_REQUEST_PAGE;
    }
}
