package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;

/**
 * Class implements interface Command, used by administrator to get add conference page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetAddConferenceFormCommand implements Command {
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

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return CREATE_CONFERENCE_PAGE;
    }
}
