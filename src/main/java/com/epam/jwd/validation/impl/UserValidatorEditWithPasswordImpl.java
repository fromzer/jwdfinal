package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class UserValidatorEditWithPasswordImpl implements UserValidator {

    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserValidatorEditWithPasswordImpl instance;

    private UserValidatorEditWithPasswordImpl() {
    }

    public static UserValidatorEditWithPasswordImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserValidatorEditWithPasswordImpl();
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
        errors.addAll(passwordValidate(user.getPassword().trim(), bundle));
        errors.addAll(emailValidate(user.getEmail().trim(), bundle));
        return errors;
    }
}
