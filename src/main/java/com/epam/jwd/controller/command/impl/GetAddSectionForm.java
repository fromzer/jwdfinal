package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAddSectionForm implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GetAddSectionForm.class);
    public static ResponseContext CREATE_SECTION_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/addSectionForm.jsp";
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
    private ConferenceService conferenceService = ConferenceService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long id = Long.valueOf(requestContext.getParameter("confId"));
        requestContext.setAttribute("confId", id);
        try {
            requestContext.setAttribute("confTitle", conferenceService.findConferenceById(id).getTitle());
        } catch (ServiceException e) {
            logger.warn("Conference search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return CREATE_SECTION_PAGE;
    }
}
