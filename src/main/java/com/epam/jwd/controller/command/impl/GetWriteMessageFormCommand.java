package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;

/**
 * Class implements interface Command, used to get write message page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetWriteMessageFormCommand implements Command {
    public static final ResponseContext WRITE_MESSAGE_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/writeMessageForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        return WRITE_MESSAGE_PAGE;
    }
}
