package com.epam.jwd.domain.impl;

import java.io.Serializable;
import java.util.Objects;

public class Message extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = -5281001470855258467L;

    private String topic;
    private String description;
    private Long userId;
    private boolean isAnswered;

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return isAnswered == message.isAnswered && Objects.equals(topic, message.topic) && Objects.equals(description, message.description) && Objects.equals(userId, message.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, description, userId, isAnswered);
    }

    @Override
    public String toString() {
        return "Message{" +
                "topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", isAnswered=" + isAnswered +
                '}';
    }

    public static class Builder {
        private Message message;

        public Builder() {
            message = new Message();
        }

        public Builder setId(long id) {
            message.setId(id);
            return this;
        }

        public Builder setTopic(String topic) {
            message.topic = topic;
            return this;
        }

        public Builder setDescription(String description) {
            message.description = description;
            return this;
        }

        public Builder setUserId(Long userId) {
            message.userId = userId;
            return this;
        }

        public Builder setAnswered(boolean answered) {
            message.isAnswered = answered;
            return this;
        }

        public Message build() {
            return message;
        }
    }
}
