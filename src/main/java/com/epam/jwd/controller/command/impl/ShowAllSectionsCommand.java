package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.SectionService;
import com.epam.jwd.service.impl.UserSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Class implements interface Command, used to return all sections from database
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ShowAllSectionsCommand implements Command {
    public static final ResponseContext SECTION_LIST_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showAllSections.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(ShowAllSectionsCommand.class);
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
    private SectionService sectionService = SectionService.getInstance();
    private UserSectionService userSectionService = UserSectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        HttpSession session = requestContext.getSession();
        User user = (User) session.getAttribute("currentUser");
        Long id = Long.valueOf(requestContext.getParameter("confId"));
        try {
            if (user != null) {
                List<Long> sectionIdList = userSectionService.findRequestsByUserId(user.getId());
                if (sectionIdList != null) {
                    requestContext.setAttribute("registerSection", sectionIdList);
                }
            }
            requestContext.setAttribute("showSections", sectionService.findSectionByConferenceId(id));
        } catch (ServiceException e) {
            logger.warn("Sections search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return SECTION_LIST_PAGE;
    }
}
