package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Lesson;

public class Survey extends Lesson {

    public static final String URL_PROPERTY_KEY = "url";
    public static final String EDIT_URL_PROPERTY_KEY = "editUrl";

    public Survey(Entity entity) {
        super(entity);
    }
     
    public Survey(String title, String description, String editUrl, String url) {
        super(Lesson.TYPE_SURVEY, title, description);
        super.entity.setProperty(Survey.EDIT_URL_PROPERTY_KEY, editUrl);
        super.entity.setProperty(Survey.URL_PROPERTY_KEY, url);
    }

    public String getEditURL() {
        return (String) super.entity.getProperty(Survey.EDIT_URL_PROPERTY_KEY);
    }

    public String getURL() {
        return (String) super.entity.getProperty(Survey.URL_PROPERTY_KEY);
    }
}