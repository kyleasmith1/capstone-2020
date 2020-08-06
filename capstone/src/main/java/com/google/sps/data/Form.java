package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;

public class Form {
    public static final String FORM_ENTITY_NAME = "Form";
    public static final String EDIT_URL_PROPERTY_KEY = "editUrl";
    public static final String URL_PROPERTY_KEY = "url";
    public static final String PUBLISHED_PROPERTY_KEY = "published";

    private Entity entity;

    public Form(Entity entity) {
        this.entity = entity;
    }

    public Form(String editUrl, String url) {
        this.entity = new Entity(Form.FORM_ENTITY_NAME);
        this.entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, editUrl);
        this.entity.setProperty(Form.URL_PROPERTY_KEY, url);
        this.entity.setProperty(Form.PUBLISHED_PROPERTY_KEY, false);
    }

    public void publish() {
        this.entity.setProperty(Form.PUBLISHED_PROPERTY_KEY, true);
    }

    public void unPublish() {
        this.entity.setProperty(Form.PUBLISHED_PROPERTY_KEY, false);
    }

    public String getEditURL() {
        return (String) this.entity.getProperty(Form.EDIT_URL_PROPERTY_KEY);
    }

    public String getURL() {
        return (String) this.entity.getProperty(Form.URL_PROPERTY_KEY);
    }

    public boolean publishState() {
        return (Boolean) this.entity.getProperty(Form.PUBLISHED_PROPERTY_KEY);
    }

    public Entity getFormEntity() {
        return this.entity;
    }

    public Key getFormKey() {
        return this.entity.getKey();
    }  
}