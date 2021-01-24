package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used to return all users from database
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ShowSectionCommand implements Command {
    public static final ResponseContext SECTION_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showSectionInfo.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(ShowSectionCommand.class);
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

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long id = Long.valueOf(requestContext.getParameter("sectionId"));
        try {
            requestContext.setAttribute("showSection", sectionService.findSectionById(id));
        } catch (ServiceException e) {
            logger.warn("Show section error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return SECTION_PAGE;
    }
}
