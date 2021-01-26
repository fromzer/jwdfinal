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
import com.epam.jwd.validation.impl.UserValidatorEditWithPasswordImpl;
import com.epam.jwd.validation.impl.UserValidatorEditWithoutPasswordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class implements interface Command, used to edit user profile
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class EditProfileCommand implements Command {
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
    private static final Logger logger = LoggerFactory.getLogger(EditProfileCommand.class);
    public static ResponseContext MAIN_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=main";
        }

        @Override
        public boolean isRedirect() {
            return true;
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
    private UserService service = UserService.getInstance();

    @Override
    public ResponseContext execute(RequestContext requestContext) {
        HttpSession session = requestContext.getSession();
        Locale locale = Locale.getDefault();
        User userScope = (User) session.getAttribute("currentUser");
        String login = userScope.getLogin();
        String firstName = requestContext.getParameter("firstName");
        String lastName = requestContext.getParameter("lastName");
        String password = requestContext.getParameter("psw");
        String passwordRepeat = requestContext.getParameter("pswRepeat");
        String email = requestContext.getParameter("email");
        UserRole role = userScope.getRole();
        boolean withoutPassword = password.equals("");
        User user = setUserParams(userScope.getId(), login, firstName, lastName, password, email, role, withoutPassword);
        List<Violation> result;
        try {
            result = validateUser(locale, password, passwordRepeat, user, withoutPassword);
        } catch (ValidateException e) {
            logger.debug("Error validate user");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return getPage(requestContext, userScope, user, result, withoutPassword);
    }

    private ResponseContext getPage(RequestContext requestContext, User userScope, User user, List<Violation> result, boolean withoutPassword) {
        try {
            if (result.isEmpty()) {
                if (withoutPassword) {
                    service.updateWithoutPassword(user);
                } else {
                    service.update(user);
                }
                requestContext.getSession(true).setAttribute("currentUser", user);
            } else {
                requestContext.setAttribute("entity", user);
                requestContext.setAttribute("validateResult", result);
                requestContext.setAttribute("userData", userScope);
                return EDIT_PROFILE_PAGE;
            }
        } catch (ServiceException ex) {
            logger.warn("User edit error");
            requestContext.setAttribute("error", ex.getMessage());
            return ERROR_PAGE;
        }
        return MAIN_PAGE;
    }

    private List<Violation> validateUser(Locale locale, String password, String passwordRepeat, User user, boolean withoutPassword) throws ValidateException {
        List<Violation> result;
        if (withoutPassword) {
            UserValidator userValidator = UserValidatorEditWithoutPasswordImpl.getInstance();
            result = userValidator.apply(user, locale);
        } else {
            UserValidator userValidator = UserValidatorEditWithPasswordImpl.getInstance();
            result = userValidator.apply(user, locale);
            ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", locale);
            result.addAll(userValidator.passwordRepeatValidate(password, passwordRepeat, bundle));
        }
        return result;
    }

    private User setUserParams(Long id, String login, String firstName, String lastName, String password, String email, UserRole role, boolean withoutPassword) {
        User user;
        if (withoutPassword) {
            user = new User.Builder()
                    .setId(id)
                    .setLogin(login)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setRole(role)
                    .build();
        } else {
            user = new User.Builder()
                    .setId(id)
                    .setLogin(login)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPassword(password)
                    .setRole(role)
                    .build();
        }
        return user;
    }
}
