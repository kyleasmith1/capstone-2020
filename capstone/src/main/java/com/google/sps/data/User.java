package com.google.sps.data;

import java.io.IOException;
import java.io.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.google.sps.data.Classroom;
import com.google.sps.data.Student;
import com.google.sps.data.Form;

import com.google.sps.service.DatabaseService;

public abstract class User {
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
        DatabaseService.save(this.delegate);
    }

    public String getEmail() {
        return (String) this.delegate.getProperty(EMAIL);
    }

    public String getNickname() {
        return (String) this.delegate.getProperty(NICKNAME);
    }

    public Entity getUserEntity() {
        return (Entity) this.delegate;
    }
}