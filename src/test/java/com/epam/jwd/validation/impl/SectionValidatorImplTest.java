package com.epam.jwd.validation.impl;

import com.epam.jwd.domain.impl.Section;
import com.epam.jwd.exception.ValidateException;
import com.epam.jwd.validation.Violation;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

public class SectionValidatorImplTest {
    private Locale locale = Locale.getDefault();
    private Section sectionPositive = new Section.Builder()
            .setTitle("Test Section")
            .setDescription("The first Section ")
            .build();
    private Section sectionNegative = new Section.Builder()
            .setTitle("Te")
            .setDescription("The first")
            .build();

    @Test
    public void positiveTestApply() {
        List<Violation> violationList = SectionValidatorImpl.getInstance().apply(sectionPositive, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, true);
    }

    @Test
    public void negativeTestApply() {
        List<Violation> violationList = SectionValidatorImpl.getInstance().apply(sectionNegative, locale);
        boolean isEmpty = violationList.isEmpty();
        assertEquals(isEmpty, false);
    }
}