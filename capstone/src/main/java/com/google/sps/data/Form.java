package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.Lesson;

public class Form extends Lesson {
    public static final String EDIT_URL_PROPERTY_KEY = "editUrl";
    public static final String URL_PROPERTY_KEY = "url";

    public Form(Entity entity) {
        super(entity);
    }
     
    public Form(String title, String description, String editUrl, String url) {
        super(Lesson.TYPE_FORM, title, description);
        super.entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, editUrl);
        super.entity.setProperty(Form.URL_PROPERTY_KEY, url);
    }

    public String getEditUrl() {
        return (String) super.entity.getProperty(Form.EDIT_URL_PROPERTY_KEY);
    }

    public String getUrl() {
        return (String) super.entity.getProperty(Form.URL_PROPERTY_KEY);
    }

    public Entity getFormEntity() {
        return super.entity;
    }
}