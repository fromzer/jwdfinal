package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import com.epam.jwd.service.impl.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetEditSectionFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GetEditSectionFormCommand.class);
    public static ResponseContext EDIT_SECTION_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/editSectionForm.jsp";
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
    private SectionService sectionService = SectionService.getInstance();
    private ConferenceService conferenceService = ConferenceService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long sectionId = Long.valueOf(requestContext.getParameter("sectionId"));
        Long conferenceId = Long.valueOf(requestContext.getParameter("confId"));
        try {
            requestContext.setAttribute("section", sectionService.findSectionById(sectionId));
            requestContext.setAttribute("confId", conferenceId);
            requestContext.setAttribute("confTitle", conferenceService.findConferenceById(conferenceId).getTitle());
        } catch (ServiceException e) {
            logger.warn("Conference and section search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_SECTION_PAGE;
    }
}
