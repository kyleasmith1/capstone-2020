package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;

public class User {
    public static final String USER_ENTITY_NAME = "User";
    public static final String NICKNAME_PROPERTY_KEY = "nickname";
    public static final String USER_ID_PROPERTY_KEY = "userId";

    private Entity delegate;

    public User(Entity delegate) {
        this.delegate = delegate;
    }

    public User(String id, String nickname) {
        this.delegate = new Entity(User.USER_ENTITY_NAME);
        this.delegate.setProperty(User.NICKNAME_PROPERTY_KEY, nickname);
        this.delegate.setProperty(User.USER_ID_PROPERTY_KEY, id);
    }

    public void changeNickname(String nickname) {
        this.delegate.setProperty(User.NICKNAME_PROPERTY_KEY, nickname);
    }

    public String getId() {
        return (String) this.delegate.getProperty(User.USER_ID_PROPERTY_KEY);
    }

    public String getNickname() {
        return (String) this.delegate.getProperty(User.NICKNAME_PROPERTY_KEY);
    }

    public Entity getUserEntity() {
        return this.delegate;
    }

    public Key getUserKey() {
        return this.delegate.getKey();
    }
}