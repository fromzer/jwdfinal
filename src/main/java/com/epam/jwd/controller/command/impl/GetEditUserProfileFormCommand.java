package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;

import javax.servlet.http.HttpSession;

/**
 * Class implements interface Command, used to get edit user profile page
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class GetEditUserProfileFormCommand implements Command {
    public static final ResponseContext EDIT_PROFILE_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/editUserProfileForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        HttpSession session = requestContext.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user != null) {
            requestContext.setAttribute("userData", user);
        }
        return EDIT_PROFILE_PAGE;
    }
}
