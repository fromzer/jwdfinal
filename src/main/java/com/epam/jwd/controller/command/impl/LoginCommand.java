package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.service.impl.UserService;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.UserValidatorLogInImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Class implements interface Command, used to log in
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class LoginCommand implements Command {
    public static final ResponseContext MAIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=main";
        }

        @Override
        public boolean isRedirect() {
            return true;
        }
    };
    public static final ResponseContext LOGIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/loginForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
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
    private static final Logger logger = LoggerFactory.getLogger(AddUserCommand.class);
    private UserService userService = UserService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        Locale locale = Locale.getDefault();
        String userLogin = requestContext.getParameter("userName");
        String password = requestContext.getParameter("inputPassword");
        try {
            if (!(userService.isExistUser(userLogin))) {
                requestContext.setAttribute("flag", "true");
                return LOGIN_PAGE;
            }
            User inputUser = new User.Builder()
                    .setLogin(userLogin)
                    .setPassword(password)
                    .build();
            UserValidator userValidator = UserValidatorLogInImpl.getInstance();
            List<Violation> result = userValidator.apply(inputUser, locale);
            if (!result.isEmpty()) {
                requestContext.setAttribute("login", inputUser.getLogin());
                requestContext.setAttribute("validateResult", result);
                return LOGIN_PAGE;
            } else if (userService.checkPassword(requestContext)) {
                User user = userService.findUserByLogin(userLogin);
                HttpSession session = requestContext.getSession(true);
                session.setAttribute("currentUser", user);
            } else {
                requestContext.setAttribute("flag", "true");
                return LOGIN_PAGE;
            }
        } catch (ServiceException | ValidateException e) {
            logger.warn("Login command error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return MAIN_PAGE;
    }
}
