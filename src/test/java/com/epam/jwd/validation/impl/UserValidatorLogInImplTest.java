package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.validation.Violation;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class UserValidatorLogInImplTest {
    private Locale locale = Locale.getDefault();
    private User userPositive = new User.Builder()
            .setLogin("Tiesto")
            .setPassword("password")
            .build();
    private User userNegative = new User.Builder()
            .setLogin("Ti")
            .setPassword("")
            .build();

    @Test
    public void positiveTestApply() {
        List<Violation> violationList = UserValidatorLogInImpl.getInstance().apply(userPositive, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, true);
    }

    @Test
    public void negativeTestApply() {
        List<Violation> violationList = UserValidatorLogInImpl.getInstance().apply(userNegative, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, false);
    }
}