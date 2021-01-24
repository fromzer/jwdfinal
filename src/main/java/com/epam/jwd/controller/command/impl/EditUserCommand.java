package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.Command;
import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.controller.command.ResponseContext;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserRole;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.exception.UnknownEntityException;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.service.impl.UserService;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;
import com.epam.jwd.validation.impl.UserValidatorEditWithPasswordImpl;
import com.epam.jwd.validation.impl.UserValidatorEditWithoutPasswordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class implements interface Command, used by administrator to edit user
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class EditUserCommand implements Command {
    public static final ResponseContext EDIT_USER_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/WEB-INF/jsp/editUserForm.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(EditUserCommand.class);
    public static ResponseContext USER_LIST_PAGE = new ResponseContext() {
        @Override
        public String getPage() {
            return "/home?command=showAllUsers";
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
        Locale locale = Locale.getDefault();
        Long userId = Long.valueOf(requestContext.getParameter("userId"));
        String login = requestContext.getParameter("login");
        String firstName = requestContext.getParameter("firstName");
        String lastName = requestContext.getParameter("lastName");
        String password = requestContext.getParameter("psw");
        String passwordRepeat = requestContext.getParameter("pswRepeat");
        String email = requestContext.getParameter("email");
        boolean withoutPassword = password.equals("");
        User user;
        List<Violation> result;
        UserRole role;
        try {
            role = UserRole.resolveRoleById(Long.valueOf(requestContext.getParameter("role")));
            user = setUserParams(userId, login, firstName, lastName, password, email, role, withoutPassword);
            result = validateUser(locale, password, passwordRepeat, user, withoutPassword);
        } catch (ValidateException | UnknownEntityException e) {
            logger.debug("Error validate user");
            requestContext.setAttribute("error", e.getMessage());
            return ERROR_PAGE;
        }
        return getPage(requestContext, user, result, withoutPassword);
    }

    private ResponseContext getPage(RequestContext requestContext, User user, List<Violation> result, boolean withoutPassword) {
        try {
            if (result.isEmpty()) {
                if (withoutPassword) {

                    service.updateWithoutPassword(user);
                } else {
                    service.update(user);
                }
            } else {
                requestContext.setAttribute("userData", service.findUserById(user.getId()));
                requestContext.setAttribute("entity", user);
                requestContext.setAttribute("validateResult", result);
                return EDIT_USER_PAGE;
            }
        } catch (ServiceException ex) {
            logger.warn("User edit error");
            requestContext.setAttribute("error", ex.getMessage());
            return ERROR_PAGE;
        }
        return USER_LIST_PAGE;
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

    private User setUserParams(Long id, String login, String firstName, String lastName, String password, String email, UserRole role, Boolean withoutPassword) {
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
                    .setRole(role)
                    .setPassword(password)
                    .build();
        }
        return user;
    }
}
