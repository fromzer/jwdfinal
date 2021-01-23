package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.validation.Violation;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class UserValidatorEditWithoutPasswordImplTest {
    private Locale locale = Locale.getDefault();
    private User userPositive = new User.Builder()
            .setFirstName("Pupkin")
            .setLastName("Vasiliy")
            .setEmail("tiesto@pupmail.org")
            .build();
    private User userNegative = new User.Builder()
            .setFirstName("")
            .setLastName("Vasiliy")
            .setEmail("tiesto@pupmail.00")
            .build();

    @Test
    public void positiveTestApply() {
        List<Violation> violationList = UserValidatorEditWithoutPasswordImpl.getInstance().apply(userPositive, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, true);
    }

    @Test
    public void negativeTestApply() {
        List<Violation> violationList = UserValidatorEditWithoutPasswordImpl.getInstance().apply(userNegative, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, false);
    }
}