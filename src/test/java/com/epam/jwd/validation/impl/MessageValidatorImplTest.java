package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.validation.Violation;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class MessageValidatorImplTest {
    private Locale locale = Locale.getDefault();
    private Message messagePositive = new Message.Builder()
            .setTopic("Hello!")
            .setDescription("Hello world!")
            .build();
    private Message messageNegative = new Message.Builder()
            .setTopic("Hi")
            .setDescription("Hello world!")
            .build();

    @Test
    public void positiveTestApply() {
        List<Violation> violationList = MessageValidatorImpl.getInstance().apply(messagePositive, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, true);
    }

    @Test
    public void negativeTestApply() {
        List<Violation> violationList = MessageValidatorImpl.getInstance().apply(messageNegative, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, false);
    }
}