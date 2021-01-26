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

/**
 * Class implements interface Command, used by administrator to update section info
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class UpdateSectionCommand implements Command {
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
    public static ResponseContext EDIT_SECTIONS_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showAndEditSectionsForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    public static ResponseContext EDIT_SECTION_FORM = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/editSectionForm.jsp";
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
        Long confId = Long.valueOf(requestContext.getParameter("confId"));
        Long sectionId = Long.valueOf(requestContext.getParameter("sectionId"));
        String title = requestContext.getParameter("sectionTitle");
        String description = requestContext.getParameter("sectionDescription");
        Section section = new Section.Builder()
                .setId(sectionId)
                .setTitle(title)
                .setDescription(description)
                .setConferenceId(confId)
                .build();
        SectionValidator sectionValidator = SectionValidatorImpl.getInstance();
        List<Violation> result = sectionValidator.apply(section, locale);
        requestContext.setAttribute("confId", confId);
        return getPage(requestContext, confId, section, result);
    }

    private ResponseContext getPage(RequestContext requestContext, Long confId, Section section, List<Violation> result) {
        try {
            if (result.isEmpty()) {
                sectionService.update(section);
                requestContext.setAttribute("showSections", sectionService.findSectionByConferenceId(confId));
            } else {
                requestContext.setAttribute("confTitle", conferenceService.findConferenceById(confId).getTitle());
                requestContext.setAttribute("entity", section);
                requestContext.setAttribute("validateResult", result);
                return EDIT_SECTION_FORM;
            }
        } catch (ServiceException e) {
            logger.warn("Section update error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_SECTIONS_PAGE;
    }
}
