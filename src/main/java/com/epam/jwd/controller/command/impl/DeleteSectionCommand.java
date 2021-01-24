package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to delete section
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class DeleteSectionCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteSectionCommand.class);
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

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long sectionId = Long.valueOf(requestContext.getParameter("sectionId"));
        Long confId = Long.valueOf(requestContext.getParameter("confId"));
        Section section;
        try {
            section = sectionService.findSectionById(sectionId);
            sectionService.delete(section);
            requestContext.setAttribute("showSections", sectionService.findSectionByConferenceId(confId));
        } catch (ServiceException e) {
            logger.warn("Section deleting error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        requestContext.setAttribute("confId", confId);
        return EDIT_SECTIONS_PAGE;
    }
}
