package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.validation.UserValidator;
import com.epam.jwd.validation.Violation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class UserValidatorLogInImpl implements UserValidator {

    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserValidatorLogInImpl instance;

    private UserValidatorLogInImpl() {
    }

    public static UserValidatorLogInImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserValidatorLogInImpl();
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
        List<Violation> errors = loginValidate(user.getLogin().trim(), bundle);
        errors.addAll(passwordValidate(user.getPassword().trim(), bundle));
        return errors;
    }
}
