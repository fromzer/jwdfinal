package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to get edit conference page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetEditConferenceFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GetEditConferenceFormCommand.class);
    public static ResponseContext EDIT_CONFERENCE_FORM = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/editConferenceForm.jsp";
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
            requestContext.setAttribute("conference", conferenceService.findConferenceById(id));
        } catch (ServiceException e) {
            logger.warn("Conference search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_CONFERENCE_FORM;
    }
}
