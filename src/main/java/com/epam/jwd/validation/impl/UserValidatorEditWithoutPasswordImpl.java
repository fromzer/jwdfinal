package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User validator implementation for edit user data process without password
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class UserValidatorEditWithoutPasswordImpl implements UserValidator {

    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserValidatorEditWithoutPasswordImpl instance;

    private UserValidatorEditWithoutPasswordImpl() {
    }

    public static UserValidatorEditWithoutPasswordImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserValidatorEditWithoutPasswordImpl();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public List<Violation> apply(User user, Locale local) {
        ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", local);
        List<Violation> errors = firstNameValidate(user.getFirstName().trim(), bundle);
        errors.addAll(lastNameValidate(user.getLastName().trim(), bundle));
        errors.addAll(emailValidate(user.getEmail().trim(), bundle));
        return errors;
    }
}
