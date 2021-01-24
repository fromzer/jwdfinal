package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.validation.ConferenceValidator;
import com.epam.jwd.validation.Violation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Conference validator implementation for creating and editing conference data
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ConferenceValidatorImpl implements ConferenceValidator {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConferenceValidatorImpl instance;

    private ConferenceValidatorImpl() {
    }

    public static ConferenceValidatorImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new ConferenceValidatorImpl();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public List<Violation> apply(Conference conference, Locale local) {
        ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", local);
        List<Violation> errors = titleValidate(conference.getTitle().trim(), bundle);
        errors.addAll(descriptionValidate(conference.getDescription().trim(), bundle));
        errors.addAll(dateStartValidate(conference.getDateStart(), bundle));
        errors.addAll(dateEndValidate(conference.getDateStart(), conference.getDateEnd(), bundle));
        return errors;
    }
}
