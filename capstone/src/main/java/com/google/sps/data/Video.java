package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;
import com.google.sps.data.Lesson;

public class Video extends Lesson {

    public static final String URL_PROPERTY_KEY = "url";

    public Video(Entity entity) {
        super(entity);
    }
     
    public Video(String title, String description, String url) {
        super(Lesson.TYPE_VIDEO, title, description);
        super.entity.setProperty(Video.URL_PROPERTY_KEY, url);
    }

    public String getURL() {
        return (String) super.entity.getProperty(Video.URL_PROPERTY_KEY);
    }
}