package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Lesson;

public class Image extends Lesson {

    public static final String URL_PROPERTY_KEY = "url";

    public Image(Entity entity) {
        super(entity);
    }
     
    public Image(String title, String description, String url) {
        super(Lesson.TYPE_IMAGE, title, description);
        super.entity.setProperty(Image.URL_PROPERTY_KEY, url);
    }

    public String getURL() {
        return (String) super.entity.getProperty(Image.URL_PROPERTY_KEY);
    }
}