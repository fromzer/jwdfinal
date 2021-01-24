package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to get show and edit conferences page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetShowAndEditConferencesFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GetShowAndEditConferencesFormCommand.class);
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
    public static ResponseContext EDIT_CONFERENCE_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showAndEditConferenceForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private ConferenceService conferenceService = ConferenceService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        try {
            requestContext.setAttribute("showConferences", conferenceService.findAllConference());
        } catch (ServiceException e) {
            logger.warn("Conferences search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_CONFERENCE_PAGE;
    }
}
