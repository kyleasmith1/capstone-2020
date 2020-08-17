package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Lesson;

public class Form extends Lesson {

    public static final String URL_PROPERTY_KEY = "url";
    public static final String EDIT_URL_PROPERTY_KEY = "editUrl";

    public Form(Entity entity) {
        super(entity);
    }
     
    public Form(String title, String description, String editUrl, String url) {
        super(Lesson.TYPE_FORM, title, description);
        super.entity.setProperty(Form.EDIT_URL_PROPERTY_KEY, editUrl);
        super.entity.setProperty(Form.URL_PROPERTY_KEY, url);
    }

    public String getEditURL() {
        return (String) super.entity.getProperty(Form.EDIT_URL_PROPERTY_KEY);
    }

    public String getURL() {
        return (String) super.entity.getProperty(Form.URL_PROPERTY_KEY);
    }
}