package com.epam.jwd.domain.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Conference extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 6368419749896072260L;

    private String title;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public static class Builder {
        private Conference conference;

        public Builder() {
            conference = new Conference();
        }

        public Builder setTitle(String title) {
            conference.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            conference.description = description;
            return this;
        }

        public Builder setDateStart(LocalDate dateStart) {
            conference.dateStart = dateStart;
            return this;
        }

        public Builder setDateEnd(LocalDate dateEnd) {
            conference.dateEnd = dateEnd;
            return this;
        }

        public Builder setId(Long id) {
            conference.setId(id);
            return this;
        }

        public Conference build() {
            return conference;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conference that = (Conference) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(dateStart, that.dateStart) && Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, dateStart, dateEnd);
    }

    @Override
    public String toString() {
        return "Conference{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
