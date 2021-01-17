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

public interface UserValidator {

    List<Violation> apply(User user, Locale locale) throws ValidateException;

    default List<Violation> loginValidate(String login, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = login.trim().length();
        if (size < 3 || size > 50) {
            result.add(new Violation("login", bundle.getString("error.registration.login")));
        }
        return result;
    }

    default List<Violation> loginUsedValidate(String login, ResourceBundle bundle, UserService service) throws ServiceException {
        var result = new ArrayList<Violation>(1);
        boolean isUsed = service.isExistUser(login);
        if (isUsed) {
            result.add(new Violation("login", bundle.getString("error.registration.login.isUsed")));
        }
        return result;
    }


    default List<Violation> firstNameValidate(String name, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = name.trim().length();
        if (size < 3 || size > 100) {
            result.add(new Violation("firstName", bundle.getString("error.registration.name")));
        }
        return result;
    }

    default List<Violation> lastNameValidate(String name, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = name.trim().length();
        if (size < 3 || size > 100) {
            result.add(new Violation("lastName", bundle.getString("error.registration.name")));
        }
        return result;
    }

    default List<Violation> passwordValidate(String password, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = password.trim().length();
        if (size < 3 || size > 40) {
            result.add(new Violation("password", bundle.getString("error.registration.password")));
        }
        return result;
    }

    default List<Violation> passwordRepeatValidate(String password, String passwordRepeat, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = password.trim().length();
        if (!password.equals(passwordRepeat)) {
            result.add(new Violation("passwordRepeat", bundle.getString("error.registration.passwordRepeat")));
        }
        return result;
    }

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
