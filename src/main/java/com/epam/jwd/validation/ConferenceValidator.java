package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.Conference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public interface ConferenceValidator {

    List<Violation> apply(Conference conference, Locale local);

    default List<Violation> titleValidate(String title, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = title.trim().length();
        if (size < 3 || size > 500) {
            result.add(new Violation("title", bundle.getString("error.conference.title")));
        }
        return result;
    }

    default List<Violation> descriptionValidate(String description, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = description.trim().length();
        if (size < 10 || size > 1000) {
            result.add(new Violation("description", bundle.getString("error.conference.description")));
        }
        return result;
    }

    default List<Violation> dateStartValidate(LocalDate date, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        LocalDate now = LocalDate.now();
        if (date.isBefore(now)) {
            result.add(new Violation("dateStart", bundle.getString("error.conference.dateBefore")));
        }
        return result;
    }

    default List<Violation> dateEndValidate(LocalDate dateStart, LocalDate dateEnd, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        if (dateEnd.isBefore(dateStart)) {
            result.add(new Violation("dateEnd", bundle.getString("error.conference.dateEnd")));
        }
        return result;
    }
}
