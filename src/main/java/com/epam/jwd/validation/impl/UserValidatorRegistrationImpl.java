package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.service.impl.UserService;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User validator implementation for registration process
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class UserValidatorRegistrationImpl implements UserValidator {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserValidatorRegistrationImpl instance;
    private Logger logger = LoggerFactory.getLogger(UserValidatorRegistrationImpl.class);

    private UserValidatorRegistrationImpl() {
    }

    public static UserValidatorRegistrationImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserValidatorRegistrationImpl();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public List<Violation> apply(User user, Locale locale) throws ValidateException {
        ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", locale);
        UserService service = UserService.getInstance();
        List<Violation> errors = loginValidate(user.getLogin().trim(), bundle);
        try {
            errors.addAll(loginUsedValidate(user.getLogin().trim(), bundle, service));
        } catch (ServiceException e) {
            logger.debug("Service exception, login is used: " + e);
            throw new ValidateException();
        }
        errors.addAll(firstNameValidate(user.getFirstName().trim(), bundle));
        errors.addAll(lastNameValidate(user.getLastName().trim(), bundle));
        errors.addAll(passwordValidate(user.getPassword().trim(), bundle));
        errors.addAll(emailValidate(user.getEmail().trim(), bundle));
        return errors;
    }
}
