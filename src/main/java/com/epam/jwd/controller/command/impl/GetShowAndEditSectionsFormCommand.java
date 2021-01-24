package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.impl.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class implements interface Command, used by administrator to get show and edit sections page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetShowAndEditSectionsFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GetShowAndEditSectionsFormCommand.class);
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
    public static ResponseContext EDIT_SECTION_FORM = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/showAndEditSectionsForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private SectionService sectionService = SectionService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Long id = Long.valueOf(requestContext.getParameter("confId"));
        requestContext.setAttribute("confId", id);
        try {
            requestContext.setAttribute("showSections", sectionService.findSectionByConferenceId(id));
        } catch (ServiceException e) {
            logger.warn("Sections search error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return EDIT_SECTION_FORM;
    }
}
