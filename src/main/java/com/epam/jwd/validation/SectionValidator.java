package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for validating section object fields
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface SectionValidator {

    /**
     * Allow collecting various options for validating section data using default methods
     *
     * @param section object for validation
     * @param locale  current language for violation messages
     * @return List of violations
     */
    List<Violation> apply(Section section, Locale locale);

    /**
     * Validate section title
     * Rule: it must be 3-500 chars long
     *
     * @param title  section title
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> titleValidate(String title, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = title.trim().length();
        if (size < 3 || size > 500) {
            result.add(new Violation("title", bundle.getString("error.section.title")));
        }
        return result;
    }

    /**
     * Validate section description
     * Rule: it must be 10-1000 chars long
     *
     * @param description section description
     * @param bundle      language bundle to use
     * @return List of violations
     */
    default List<Violation> descriptionValidate(String description, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = description.trim().length();
        if (size < 10 || size > 1000) {
            result.add(new Violation("description", bundle.getString("error.section.description")));
        }
        return result;
    }
}
