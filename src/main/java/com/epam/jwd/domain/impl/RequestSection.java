package com.epam.jwd.domain.impl;

import java.util.Objects;

public class RequestSection {
    private Conference conference;
    private Section section;
    private UserSection userSection;
    private User user;

    public Conference getConference() {
        return conference;
    }

    public Section getSection() {
        return section;
    }

    public UserSection getUserSection() {
        return userSection;
    }

    public User getUser() {
        return user;
    }

    public static class Builder {
        private RequestSection requestSection;

        public Builder() {
            requestSection = new RequestSection();
        }

        public Builder withConference(Conference conference) {
            requestSection.conference = conference;
            return this;
        }

        public Builder withSection(Section section) {
            requestSection.section = section;
            return this;
        }

        public Builder withUserSection(UserSection userSection) {
            requestSection.userSection = userSection;
            return this;
        }

        public Builder withUser(User user) {
            requestSection.user = user;
            return this;
        }

        public RequestSection build() {
            return requestSection;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestSection that = (RequestSection) o;
        return Objects.equals(conference, that.conference) && Objects.equals(section, that.section) && Objects.equals(userSection, that.userSection) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conference, section, userSection, user);
    }

    @Override
    public String toString() {
        return "RequestSection{" +
                "conference=" + conference +
                ", section=" + section +
                ", userSection=" + userSection +
                ", user=" + user +
                '}';
    }
}
