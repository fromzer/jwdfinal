package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for validating message object fields
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface MessageValidator {

    /**
     * Allow collecting various options for validating message data using default methods
     *
     * @param message object for validation
     * @param locale  current language for violation messages
     * @return List of violations
     */
    List<Violation> apply(Message message, Locale locale);

    /**
     * Validate message topic
     * Rule: it must be 3-255 chars long
     *
     * @param topic  topic to check
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> topicValidate(String topic, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = topic.trim().length();
        if (size < 3 || size > 255) {
            result.add(new Violation("topic", bundle.getString("error.sendMessage.topic")));
        }
        return result;
    }

    /**
     * Validate message
     * Rule: it must be 3-255 chars long
     *
     * @param message message to check
     * @param bundle  language bundle to use
     * @return List of violations
     */
    default List<Violation> messageValidate(String message, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = message.trim().length();
        if (size < 3 || size > 255) {
            result.add(new Violation("message", bundle.getString("error.sendMessage.description")));
        }
        return result;
    }
}
