package com.epam.jwd.domain.impl;

import java.io.Serializable;
import java.util.Objects;


/**
 * Section class describes sections of conference
 * 
 * @author Egor Miheev
 * @version 1.0.0
 */
public class Section extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 4882087911198229020L;

    /**
     * Section title
     */
    private String title;

    /**
     * Section description
     */
    private String description;

    /**
     * Conference the section belongs to
     */
    private Long conferenceId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public static class Builder {
        private Section section;

        public Builder() {
            section = new Section();
        }

        public Builder setId(Long id) {
            section.setId(id);
            return this;
        }

        public Builder setTitle(String title) {
            section.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            section.description = description;
            return this;
        }

        public Builder setConferenceId(Long conferenceId) {
            section.conferenceId = conferenceId;
            return this;
        }

        public Section build() {
            return section;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(title, section.title) && Objects.equals(description, section.description) && Objects.equals(conferenceId, section.conferenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, conferenceId);
    }

    @Override
    public String toString() {
        return "Section{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", conferenceId=" + conferenceId +
                '}';
    }
}
