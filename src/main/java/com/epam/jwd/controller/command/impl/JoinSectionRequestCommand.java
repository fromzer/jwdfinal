package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.State;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserSection;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * Class implements interface Command, used to join section
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class JoinSectionRequestCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(JoinSectionRequestCommand.class);
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
    public static ResponseContext SECTION_LIST_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=sections";
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
        Long sectionId = Long.valueOf(requestContext.getParameter("sectionId"));
        User user = (User) session.getAttribute("currentUser");
        UserSection userSection = new UserSection.Builder()
                .setUserId(user.getId())
                .setSectionId(sectionId)
                .setState(State.CONSIDERED)
                .build();
        try {
            userSectionService.create(userSection);
        } catch (ServiceException e) {
            logger.warn("Join section error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return SECTION_LIST_PAGE;
    }
}
