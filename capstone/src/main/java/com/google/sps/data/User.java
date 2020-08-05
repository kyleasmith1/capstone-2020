package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;

public class User {
    protected static final String NICKNAME = "nickname";
    protected static final String USER = "User";
    protected static final String ID = "userId";

    protected Entity delegate;

    public User(Entity delegate) {
        this.delegate = delegate;
    }

    public User(String userId, String nickname) {
        this.delegate = new Entity(User.USER);
        this.delegate.setProperty(User.NICKNAME, nickname);
        this.delegate.setProperty(User.ID, userId);
    }

    public void changeNickname(String nickname) {
        this.delegate.setProperty(User.NICKNAME, nickname);
    }

    public String getID() {
        return (String) this.delegate.getProperty(User.ID);
    }

    public String getNickname() {
        return (String) this.delegate.getProperty(User.NICKNAME);
    }

    public Entity getUserEntity() {
        return this.delegate;
    }

    public Key getUserKey() {
        return this.delegate.getKey();
    }
}