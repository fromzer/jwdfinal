package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.validation.Violation;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class UserValidatorEditWithPasswordImplTest {
    private Locale locale = Locale.getDefault();
    private User userPositive = new User.Builder()
            .setFirstName("Pupkin")
            .setLastName("Vasiliy")
            .setPassword("password")
            .setEmail("tiesto@pupmail.org")
            .build();
    private User userNegative = new User.Builder()
            .setFirstName("")
            .setLastName("Va")
            .setPassword("")
            .setEmail("tiesto@pupmail.00")
            .build();

    @Test
    public void positiveTestApply() {
        List<Violation> violationList = UserValidatorEditWithPasswordImpl.getInstance().apply(userPositive, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, true);
    }

    @Test
    public void negativeTestApply() {
        List<Violation> violationList = UserValidatorEditWithPasswordImpl.getInstance().apply(userNegative, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, false);
    }
}