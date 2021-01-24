package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserSection;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.UserSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * Class implements interface Command, used to delete user request to section
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class DeleteRequestCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteRequestCommand.class);
    public static ResponseContext REQUEST_LIST_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=allUserRequests";
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
    private UserSectionService userSectionService = UserSectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        HttpSession session = requestContext.getSession();
        Long sectionId = Long.valueOf(requestContext.getParameter("sectionId"));
        User user = (User) session.getAttribute("currentUser");
        UserSection userSection = new UserSection.Builder()
                .setUserId(user.getId())
                .setSectionId(sectionId)
                .build();
        try {
            userSectionService.delete(userSection);
        } catch (ServiceException e) {
            logger.warn("Request deleting error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return REQUEST_LIST_PAGE;
    }
}
