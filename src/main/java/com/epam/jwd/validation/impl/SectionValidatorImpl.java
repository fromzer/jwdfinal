package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.validation.SectionValidator;
import com.epam.jwd.validation.Violation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Section validator implementation for creating and editing section data
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class SectionValidatorImpl implements SectionValidator {
    private static SectionValidatorImpl instance;
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);

    public static SectionValidatorImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new SectionValidatorImpl();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    private SectionValidatorImpl() {
    }

    @Override
    public List<Violation> apply(Section section, Locale local) {
        ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", local);
        List<Violation> errors = titleValidate(section.getTitle().trim(), bundle);
        errors.addAll(descriptionValidate(section.getDescription().trim(), bundle));
        return errors;
    }
}
