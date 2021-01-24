package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Class implements interface Command, used to get main page
 * return all actual conferences from database, whose end date is greater than the current date
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class MainCommand implements Command {
    public static final ResponseContext MAIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/main.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(MainCommand.class);
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
        long currentPage;
        long recordsPerPage;
        try {
            currentPage = Long.parseLong(requestContext.getParameter("currentPage"));
            recordsPerPage = Long.parseLong(requestContext.getParameter("recordsPerPage"));
        } catch (NumberFormatException ex) {
            currentPage = 1;
            recordsPerPage = 5;
        }
        List<Conference> conferences;
        try {
            conferences = conferenceService.findConferenceByLimit(currentPage, recordsPerPage);
            Long rows = conferenceService.getNumberOfConferences();
            long numberOfPages = rows / recordsPerPage;
            if (numberOfPages % recordsPerPage > 0) {
                numberOfPages++;
            }
            requestContext.setAttribute("numberOfPages", numberOfPages);
            requestContext.setAttribute("currentPage", currentPage);
            requestContext.setAttribute("recordsPerPage", recordsPerPage);
            requestContext.setAttribute("showConferences", conferences);
        } catch (ServiceException e) {
            logger.warn("Conferences search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return MAIN_PAGE;
    }
}
