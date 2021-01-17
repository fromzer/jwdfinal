package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.ConferenceService;
import com.epam.jwd.service.impl.SectionService;
import com.epam.jwd.validation.SectionValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.SectionValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

public class AddSectionCommand implements Command {
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
    private static final Logger logger = LoggerFactory.getLogger(AddSectionCommand.class);
    public static ResponseContext EDIT_SECTION_IN_CONFERENCE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showAndEditSectionsForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
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
    private ConferenceService conferenceService = ConferenceService.getInstance();
    private SectionService sectionService = SectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Locale locale = Locale.getDefault();
        Long id = Long.valueOf(requestContext.getParameter("confId"));
        String title = requestContext.getParameter("sectionTitle");
        String description = requestContext.getParameter("sectionDescription");
        Section section = new Section.Builder()
                .setTitle(title)
                .setDescription(description)
                .setConferenceId(id)
                .build();
        SectionValidator sectionValidator = SectionValidatorImpl.getInstance();
        List<Violation> result = sectionValidator.apply(section, locale);
        requestContext.setAttribute("confId", id);
        return getPage(requestContext, id, section, result);
    }

    private ResponseContext getPage(RequestContext requestContext, Long id, Section section, List<Violation> result) {
        try {
            if (result.isEmpty()) {
                sectionService.create(section);
                requestContext.setAttribute("showSections", sectionService.findSectionByConferenceId(id));
            } else {
                requestContext.setAttribute("confTitle", conferenceService.findConferenceById(id).getTitle());
                requestContext.setAttribute("entity", section);
                requestContext.setAttribute("validateResult", result);
                return CREATE_SECTION_PAGE;
            }
        } catch (ServiceException e) {
            logger.warn("Section creation error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_SECTION_IN_CONFERENCE;
    }
}
