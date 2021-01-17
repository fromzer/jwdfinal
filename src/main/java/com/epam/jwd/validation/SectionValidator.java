package com.epam.jwd.validation;

import com.epam.jwd.domain.impl.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public interface SectionValidator {

    List<Violation> apply(Section section, Locale local);

    default List<Violation> titleValidate(String title, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = title.trim().length();
        if (size < 3 || size > 500) {
            result.add(new Violation("title", bundle.getString("error.section.title")));
        }
        return result;
    }

    default List<Violation> descriptionValidate(String description, ResourceBundle bundle) {
        var result = new ArrayList<Violation>(1);
        int size = description.trim().length();
        if (size < 10 || size > 1000) {
            result.add(new Violation("description", bundle.getString("error.section.description")));
        }
        return result;
    }
}
