package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;

public class Form {
    protected static final String FORM = "Form";
    protected static final String EDIT = "editUrl";
    protected static final String URL = "Url";
    protected static final String PUBLISHED = "published";

    protected Entity entity;

    public Form(Entity entity) {
        this.entity = entity;
    }

    public Form(String editUrl, String Url) {
        this.entity = new Entity(Form.FORM);
        this.entity.setProperty(Form.EDIT, editUrl);
        this.entity.setProperty(Form.URL, Url);
        this.entity.setProperty(Form.PUBLISHED, false);
    }

    public void publish() {
        this.entity.setProperty(Form.PUBLISHED, true);
    }

    public void unPublish() {
        this.entity.setProperty(Form.PUBLISHED, false);
    }

    public String getEditURL() {
        return (String) this.entity.getProperty(Form.EDIT);
    }

    public String getURL() {
        return (String) this.entity.getProperty(Form.URL);
    }

    public boolean publishState() {
        return (Boolean) this.entity.getProperty(Form.PUBLISHED);
    }

    public Entity getFormEntity() {
        return this.entity;
    }

    public Key getFormKey() {
        return this.entity.getKey();
    }  
}