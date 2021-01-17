package com.epam.jwd.domain.impl;

import java.io.Serializable;
import java.util.Objects;

public class UserSection extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 4391017142853002095L;

    private Long userId;
    private Long sectionId;
    private State state;

    public Long getUserId() {
        return userId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public State getState() {
        return state;
    }

    public static class Builder {
        private UserSection userSection;

        public Builder() {
            userSection = new UserSection();
        }

        public Builder setId(Long id) {
            userSection.setId(id);
            return this;
        }

        public Builder setUserId(Long userId) {
            userSection.userId = userId;
            return this;
        }

        public Builder setSectionId(Long sectionId) {
            userSection.sectionId = sectionId;
            return this;
        }

        public Builder setState(State state) {
            userSection.state = state;
            return this;
        }

        public UserSection build() {
            return userSection;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSection that = (UserSection) o;
        return Objects.equals(userId, that.userId) && Objects.equals(sectionId, that.sectionId) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sectionId, state);
    }

    @Override
    public String toString() {
        return "UserSection{" +
                "userId=" + userId +
                ", sectionId=" + sectionId +
                ", state=" + state +
                '}';
    }
}
