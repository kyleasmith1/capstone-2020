package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Lesson;
import java.util.List;
import java.util.ArrayList;

public class Content extends Lesson {

    public static final String CONTENT_PROPERTY_KEY = "content";
    public static final String URL_LIST_PROPERTY_KEY = "urls";

    public Content(Entity entity) {
        super(entity);
    }
     
    public Content(String title, String description, String content, List<String> urls) {
        super(Lesson.TYPE_CONTENT, title, description);
        super.entity.setProperty(Content.CONTENT_PROPERTY_KEY, content);
        super.entity.setProperty(Content.URL_LIST_PROPERTY_KEY, urls);
    }

    public String getContent() {
        return (String) super.entity.getProperty(Content.CONTENT_PROPERTY_KEY);
    }

    public List<String> getURLs() {
        return (ArrayList<String>) super.entity.getProperty(Content.URL_LIST_PROPERTY_KEY);
    }

    public void addURL(String url) {
        List<String> urls = (ArrayList<String>) super.entity.getProperty(Content.URL_LIST_PROPERTY_KEY);

        if(urls == null){
            urls = new ArrayList<>();
        }
        urls.add(url);
    }
}