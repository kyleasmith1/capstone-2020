package com.google.sps.data;

import java.io.IOException;
import java.io.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.google.sps.data.User;
import com.google.sps.data.Classroom;

import com.google.sps.service.DatabaseService;

public class Form {

    private Entity entity;

    public Form(Entity entity) {
        this.entity = entity;
    }

    public Form(String editURL, String URL, boolean isPublished) {
        this.entity = new Entity("Form");
        this.entity.setProperty("editURL", editURL);
        this.entity.setProperty("URL", URL);
        this.entity.setProperty("published", isPublished);
    }

    public void publish() {
        this.entity.setProperty("published", true);
        DatabaseService.save(this.entity);
    }

    public void unPublish() {
        this.entity.setProperty("published", false);
        DatabaseService.save(this.entity);
    }

    public String getEditURL() {
        return (String) this.entity.getProperty("editURL");
    }

    public String getURL() {
        return (String) this.entity.getProperty("URL");
    }

    public boolean publishState() {
        return (Boolean) this.entity.getProperty("published");
    }

    public Entity getFormEntity() {
        return (Entity) this.entity;
    }
}