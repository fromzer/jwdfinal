package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.service.impl.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface for validating user object fields
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface UserValidator {

    /**
     * Allow collecting various options for validating user data using default methods
     *
     * @param user   object for validation
     * @param locale current language for violation messages
     * @return List of violations
     * @throws ValidateException
     */
    List<Violation> apply(User user, Locale locale) throws ValidateException;

    /**
     * Validate user login
     * Rule: it must be 3-50 chars long
     *
     * @param login  login to check
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> loginValidate(String login, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = login.trim().length();
        if (size < 3 || size > 50) {
            result.add(new Violation("login", bundle.getString("error.registration.login")));
        }
        return result;
    }

    /**
     * Validate whether login is already used by another user
     *
     * @param login  login to check
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> loginUsedValidate(String login, ResourceBundle bundle, UserService service) throws ServiceException {
        var result = new ArrayList<Violation>(1);
        boolean isUsed = service.isExistUser(login);
        if (isUsed) {
            result.add(new Violation("login", bundle.getString("error.registration.login.isUsed")));
        }
        return result;
    }

    /**
     * Validate user's first name
     * Rule: it must be 3-100 chars long
     *
     * @param name   user's first name
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> firstNameValidate(String name, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = name.trim().length();
        if (size < 3 || size > 100) {
            result.add(new Violation("firstName", bundle.getString("error.registration.name")));
        }
        return result;
    }

    /**
     * Validate user's last name
     * Rule: it must be 3-100 chars long
     *
     * @param name   user's last name
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> lastNameValidate(String name, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = name.trim().length();
        if (size < 3 || size > 100) {
            result.add(new Violation("lastName", bundle.getString("error.registration.name")));
        }
        return result;
    }

    /**
     * Validate user's password
     * Rule: it must be 3-40 chars long
     *
     * @param password user's password
     * @param bundle   language bundle to use
     * @return List of violations
     */
    default List<Violation> passwordValidate(String password, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = password.trim().length();
        if (size < 3 || size > 40) {
            result.add(new Violation("password", bundle.getString("error.registration.password")));
        }
        return result;
    }

    /**
     * Validate whether password and repeatPassword are the same
     *
     * @param password
     * @param passwordRepeat
     * @param bundle         language bundle to use
     * @return List of violations
     */
    default List<Violation> passwordRepeatValidate(String password, String passwordRepeat, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        if (!password.equals(passwordRepeat)) {
            result.add(new Violation("passwordRepeat", bundle.getString("error.registration.passwordRepeat")));
        }
        return result;
    }

    /**
     * Validate email
     *
     * @param email  email to check
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> emailValidate(String email, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        String regex = "^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            result.add(new Violation("email", bundle.getString("error.registration.email")));
        }
        return result;
    }
}
