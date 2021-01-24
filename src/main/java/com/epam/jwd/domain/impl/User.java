package com.epam.jwd.domain.impl;

import java.io.Serializable;
import java.util.Objects;

/**
 * User class describes a user web-app entity
 * 
 * @author Egor Miheev
 * @version 1.0.0
 */
public class User extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 7977384671981758716L;
    
    /**
     * User login
     */
    private String login;

    /**
     * User first name
     */
    private String firstName;

    /**
     * User last name
     */
    private String lastName;

    /**
     * User password
     */
    private String password;

    /**
     * User email
     */
    private String email;

    /**
     * User role - admin/user, etc
     */
    private UserRole role;

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder setId(Long id) {
            user.setId(id);
            return this;
        }

        public Builder setLogin(String login) {
            user.login = login;
            return this;
        }

        public Builder setFirstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder setPassword(String password) {
            user.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            user.email = email;
            return this;
        }

        public Builder setRole(UserRole role) {
            user.role = role;
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, lastName, password, email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
