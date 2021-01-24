package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import com.epam.jwd.validation.ConferenceValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.ConferenceValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Class implements interface Command, used by administrator to update conference info
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class UpdateConferenceCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UpdateConferenceCommand.class);
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
    private ConferenceService conferenceService = ConferenceService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Locale locale = Locale.getDefault();
        Long id = Long.valueOf(requestContext.getParameter("confId"));
        String title = requestContext.getParameter("confTitle");
        String description = requestContext.getParameter("confDescription");
        String[] availableDateStart = requestContext.getParameterValues("dateStart");
        String[] availableDateEnd = requestContext.getParameterValues("dateEnd");
        LocalDate startDate = LocalDate.parse(availableDateStart[0]);
        LocalDate endDate = LocalDate.parse(availableDateEnd[0]);
        Conference conference = new Conference.Builder()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setDateStart(startDate)
                .setDateEnd(endDate)
                .build();
        ConferenceValidator conferenceValidator = ConferenceValidatorImpl.getInstance();
        List<Violation> result = conferenceValidator.apply(conference, locale);
        return getPage(requestContext, id, conference, result);
    }

    private ResponseContext getPage(RequestContext requestContext, Long id, Conference conference, List<Violation> result) {
        try {
            if (result.isEmpty()) {
                conferenceService.update(conference);
            } else {
                requestContext.setAttribute("entity", conference);
                requestContext.setAttribute("validateResult", result);
                requestContext.setAttribute("confId", id);
                requestContext.setAttribute("conference", conferenceService.findConferenceById(id));
                return EDIT_CONFERENCE_FORM;
            }
            requestContext.setAttribute("showConferences", conferenceService.findAllConference());
        } catch (ServiceException e) {
            logger.warn("Conference updating error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_CONFERENCE_PAGE;
    }
}
