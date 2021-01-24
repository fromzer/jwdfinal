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
 * Class implements interface Command, used by administrator to add new conference
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class AddConferenceCommand implements Command {
    public static final ResponseContext ERROR_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/error.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(AddConferenceCommand.class);
    public static ResponseContext EDIT_SECTION_IN_CONFERENCE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=toEditConferencesPage";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    public static ResponseContext CREATE_CONFERENCE_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/addConferenceForm.jsp";
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
        String description = requestContext.getParameter("confDescription");
        String title = requestContext.getParameter("confTitle");
        String[] availableDateStart = requestContext.getParameterValues("dateStart");
        String[] availableDateEnd = requestContext.getParameterValues("dateEnd");
        LocalDate startDate = LocalDate.parse(availableDateStart[0]);
        LocalDate endDate = LocalDate.parse(availableDateEnd[0]);
        Conference conference = new Conference.Builder()
                .setTitle(title)
                .setDescription(description)
                .setDateStart(startDate)
                .setDateEnd(endDate)
                .build();
        ConferenceValidator conferenceValidator = ConferenceValidatorImpl.getInstance();
        List<Violation> result = conferenceValidator.apply(conference, locale);
        return getPage(requestContext, conference, result);
    }

    private ResponseContext getPage(RequestContext requestContext, Conference conference, List<Violation> result) {
        if (result.isEmpty()) {
            try {
                conferenceService.create(conference);
            } catch (ServiceException e) {
                logger.warn("Conference creation error");
                requestContext.setAttribute("error", e.getMessage());
                return ERROR_PAGE;
            }
        } else {
            requestContext.setAttribute("entity", conference);
            requestContext.setAttribute("validateResult", result);
            return CREATE_CONFERENCE_PAGE;
        }
        return EDIT_SECTION_IN_CONFERENCE;
    }
}
