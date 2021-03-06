package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Class implements interface Command, used to set language
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class SetLocaleCommand implements Command {
    public static ResponseContext MAIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/index.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        String localeScope = requestContext.getParameter("locale");
        HttpSession session = requestContext.getSession(true);
        String lang = localeScope;
        if (localeScope != null && !localeScope.isEmpty()) {
            switch (localeScope) {
                case "ru_RU":
                    session.setAttribute("locale", "ru_RU");
                    lang="ru_RU";
                    break;
                case "ru_BY":
                    session.setAttribute("locale", "ru_BY");
                    lang="ru_BY";
                    break;
                default:
                    session.setAttribute("locale", "en_US");
                    lang="en_US";
                    break;
            }
        }
        if (lang != null) {
            Locale.setDefault(new Locale(lang));
        }
        return MAIN_PAGE;
    }
}
