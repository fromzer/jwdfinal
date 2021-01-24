package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.validation.MessageValidator;
import com.epam.jwd.validation.Violation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Message validator implementation for creating and editing message data
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class MessageValidatorImpl implements MessageValidator {
    private static MessageValidatorImpl instance;
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    public static MessageValidatorImpl getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new MessageValidatorImpl();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    private MessageValidatorImpl() {
    }

    @Override
    public List<Violation> apply(Message message, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("pagecontent", locale);
        List<Violation> errors = topicValidate(message.getTopic().trim(), bundle);
        errors.addAll(messageValidate(message.getDescription().trim(), bundle));
        return errors;
    }
}
