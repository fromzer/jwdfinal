package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserRole;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.service.impl.UserService;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.UserValidatorRegistrationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class implements interface Command, used by administrator to add new user
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class AddUserCommand implements Command {
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
    public static final ResponseContext REGISTRATION_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/registrationForm.jsp";
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
        String login = requestContext.getParameter("login");
        String firstName = requestContext.getParameter("firstName");
        String lastName = requestContext.getParameter("lastName");
        String password = requestContext.getParameter("psw");
        String passwordRepeat = requestContext.getParameter("psw-repeat");
        String email = requestContext.getParameter("email");
        User user = new User.Builder()
                .setLogin(login)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setEmail(email)
                .setRole(UserRole.USER)
                .build();
        return getPage(requestContext, locale, password, passwordRepeat, user);
    }

    private ResponseContext getPage(RequestContext requestContext, Locale locale, String password, String passwordRepeat, User user) {
        try {
            UserValidator userValidator = UserValidatorRegistrationImpl.getInstance();
            List<Violation> result = userValidator.apply(user, locale);
            ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", locale);
            result.addAll(userValidator.passwordRepeatValidate(password, passwordRepeat, bundle));
            if (result.isEmpty()) {
                userService.create(user);
            } else {
                requestContext.setAttribute("entity", user);
                requestContext.setAttribute("validateResult", result);
                return REGISTRATION_PAGE;
            }
        } catch (ServiceException | ValidateException e) {
            logger.warn("User creation error");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return LOGIN_PAGE;
    }
}
