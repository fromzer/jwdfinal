package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.validation.Violation;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

public class ConferenceValidatorImplTest {

    private Locale locale = Locale.getDefault();
    private Conference conferencePositive = new Conference.Builder()
            .setTitle("Test conference")
            .setDescription("The test conference")
            .setDateStart(LocalDate.of(2021, 03, 01))
            .setDateEnd(LocalDate.of(2021, 04, 01))
            .build();
    private Conference conferenceNegative = new Conference.Builder()
            .setTitle("Test conference")
            .setDescription("The ")
            .setDateStart(LocalDate.of(2022, 03, 01))
            .setDateEnd(LocalDate.of(2021, 04, 01))
            .build();

    @Test
    public void positiveTestApply() {
        List<Violation> violationList = ConferenceValidatorImpl.getInstance().apply(conferencePositive, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, true);
    }

    @Test
    public void negativeTestApply() {
        List<Violation> violationList = ConferenceValidatorImpl.getInstance().apply(conferenceNegative, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, false);
    }
}