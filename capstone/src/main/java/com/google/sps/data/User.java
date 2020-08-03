package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;

public class User {
    protected static final String NICKNAME = "nickname";
    protected static final String USER = "User";
    protected static final String EMAIL = "email";

    protected Entity delegate;

    public User(Entity delegate) {
        this.delegate = delegate;
    }

    public User(String email, String nickname) {
        this.delegate = new Entity(USER);
        this.delegate.setProperty(NICKNAME, nickname);
        this.delegate.setProperty(EMAIL, email);
    }

    public void changeNickname(String nickname) {
        this.delegate.setProperty(NICKNAME, nickname);
    }

    public String getEmail() {
        return (String) this.delegate.getProperty(EMAIL);
    }

    public String getNickname() {
        return (String) this.delegate.getProperty(NICKNAME);
    }

    public Entity getUserEntity() {
        return this.delegate;
    }

    public Key getUserKey() {
        return this.delegate.getKey();
    }
}