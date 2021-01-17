package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public interface MessageValidator {
    List<Violation> apply(Message message, Locale locale);

    default List<Violation> topicValidate(String topic, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = topic.trim().length();
        if (size < 3 || size > 255) {
            result.add(new Violation("topic", bundle.getString("error.sendMessage.topic")));
        }
        return result;
    }

    default List<Violation> messageValidate(String message, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = message.trim().length();
        if (size < 3 || size > 255) {
            result.add(new Violation("message", bundle.getString("error.sendMessage.description")));
        }
        return result;
    }
}
