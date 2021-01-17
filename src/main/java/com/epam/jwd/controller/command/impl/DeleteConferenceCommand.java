package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteConferenceCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteConferenceCommand.class);
    public static ResponseContext EDIT_CONFERENCE_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=toEditConferencesPage";
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
    private ConferenceService conferenceService = ConferenceService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long confId = Long.valueOf(requestContext.getParameter("confId"));
        Conference conference = new Conference();
        conference.setId(confId);
        try {
            conferenceService.delete(conference);
        } catch (ServiceException e) {
            logger.warn("Conference deleting error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_CONFERENCE_PAGE;
    }
}
