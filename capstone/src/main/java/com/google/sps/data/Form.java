package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;

public class Form {
    protected static final String FORM = "Form";
    protected static final String EDIT_URL = "editURL";
    protected static final String URL = "URL";
    protected static final String PUBLISHED = "published";

    protected Entity entity;

    public Form(Entity entity) {
        this.entity = entity;
    }

    public Form(String editURL, String URL, boolean isPublished) {
        this.entity = new Entity(FORM);
        this.entity.setProperty(EDIT_URL, editURL);
        this.entity.setProperty(URL, URL);
        this.entity.setProperty(PUBLISHED, isPublished);
    }

    public void publish() {
        this.entity.setProperty(PUBLISHED, true);
    }

    public void unPublish() {
        this.entity.setProperty(PUBLISHED, false);
    }

    public String getEditURL() {
        return (String) this.entity.getProperty(EDIT_URL);
    }

    public String getURL() {
        return (String) this.entity.getProperty(URL);
    }

    public boolean publishState() {
        return (Boolean) this.entity.getProperty(PUBLISHED);
    }

    public Entity getFormEntity() {
        return this.entity;
    }

    public Key getFormKey() {
        return this.entity.getKey();
    }
}