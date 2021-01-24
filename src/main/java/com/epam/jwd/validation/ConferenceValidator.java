package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.Conference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for validating conference object fields
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface ConferenceValidator {

    /**
     * Allow collecting various options for validating conference data using default methods
     *
     * @param conference object for validation
     * @param locale     current language for violation messages
     * @return List of violations
     */
    List<Violation> apply(Conference conference, Locale locale);

    /**
     * Validate conference title
     * Rule: it must be 3-500 chars long
     *
     * @param title  conference title
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> titleValidate(String title, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = title.trim().length();
        if (size < 3 || size > 500) {
            result.add(new Violation("title", bundle.getString("error.conference.title")));
        }
        return result;
    }

    /**
     * Validate conference description
     * Rule: it must be 10-1000 chars long
     *
     * @param description conference description
     * @param bundle      language bundle to use
     * @return List of violations
     */
    default List<Violation> descriptionValidate(String description, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = description.trim().length();
        if (size < 10 || size > 1000) {
            result.add(new Violation("description", bundle.getString("error.conference.description")));
        }
        return result;
    }

    /**
     * Validate conference start date
     * Rule: start date can't be before current date
     *
     * @param date   conference start date
     * @param bundle language bundle to use
     * @return List of violations
     */
    default List<Violation> dateStartValidate(LocalDate date, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        LocalDate now = LocalDate.now();
        if (date.isBefore(now)) {
            result.add(new Violation("dateStart", bundle.getString("error.conference.dateBefore")));
        }
        return result;
    }

    /**
     * Validate conference end date
     * Rule: end date must follow after start date
     *
     * @param dateStart conference start date
     * @param dateEnd   conference end date
     * @param bundle    language bundle to use
     * @return List of violations
     */
    default List<Violation> dateEndValidate(LocalDate dateStart, LocalDate dateEnd, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        if (dateEnd.isBefore(dateStart)) {
            result.add(new Violation("dateEnd", bundle.getString("error.conference.dateEnd")));
        }
        return result;
    }
}
