package com.google.sps.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.service.DatabaseService;
import java.util.Date;

public abstract class Lesson {

    public static final String LESSON_ENTITY_NAME = "Lesson";
    public static final String ISDRAFT_PROPERTY_KEY = "isDraft";
    public static final String TITLE_PROPERTY_KEY = "title";
    public static final String DESCRIPTION_PROPERTY_KEY = "description";
    // public static final String TAGS_PROPERTY_KEY = "tags";
    public static final String DATE_PROPERTY_KEY = "date";

    private Entity delegate;

    public Lesson(Entity delegate) {
        this.delegate = delegate;
    }

}