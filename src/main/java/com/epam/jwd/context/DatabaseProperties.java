package com.epam.jwd.context;

public class DatabaseProperties {
    private String dbURL;
    private String dbUser;
    private String dbPassword;
    private Integer dbPoolSize;

    public DatabaseProperties(String dbURL, String dbUser, String dbPassword, Integer dbPoolSize) {
        this.dbURL = dbURL;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbPoolSize = dbPoolSize;
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public Integer getDbPoolSize() {
        return dbPoolSize;
    }
}
